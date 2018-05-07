package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping implements HandlerMapping {
	private Object[] basePackage;

	private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

	public AnnotationHandlerMapping(Object... basePackage) {
		this.basePackage = basePackage;
	}

	public void initialize() {
		ControllerScanner controllerScanner = new ControllerScanner(this.basePackage);
		Map<Class<?>, Object> controllers = controllerScanner.getControllers();
		Map<HandlerKey, HandlerExecution> handlerMap = controllers.entrySet().stream()
			.map(entry -> getHandlerExecution(entry.getKey(), entry.getValue()).entrySet())
			.flatMap(Collection::stream).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		handlerExecutions.putAll(handlerMap);
	}

	@Override
	public HandlerExecution getHandler(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
		return handlerExecutions.get(new HandlerKey(requestUri, rm));
	}

	private Map<HandlerKey, HandlerExecution> getHandlerExecution(final Class<?> clazz, final Object declaredObject) {
		final RequestMapping requestMappingByClass = clazz.getAnnotation(RequestMapping.class);
		Set<Method> methodSet = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class));
		return methodSet.stream().map(method -> getHandlerEntry(declaredObject, requestMappingByClass, method))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	private AbstractMap.SimpleEntry<HandlerKey, HandlerExecution> getHandlerEntry(Object declaredObject, RequestMapping requestMappingByClass,
		Method method) {
		RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
		String url = requestMappingByClass != null ?
			requestMappingByClass.value() + requestMapping.value() : requestMapping.value();
		RequestMethod requestMethod = requestMapping.method();
		HandlerKey handlerKey = new HandlerKey(url, requestMethod);
		HandlerExecution handlerExecution = new HandlerExecution(declaredObject, method);
		return new AbstractMap.SimpleEntry<>(handlerKey, handlerExecution);
	}
}
