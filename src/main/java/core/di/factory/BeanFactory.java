package core.di.factory;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.common.ExceptionWrapFunction;
import core.common.ExceptionWrapSuplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Set<Class<?>> preInstanticateBeans;

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}

	public Map<Class<?>, Object> getControllers(){
		return beans.entrySet().stream()
			.filter(entry -> entry.getKey().isAnnotationPresent(Controller.class))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public void initialize() {
		try {
			preInstanticateBeans.forEach((bean) -> beans.put(bean, initializeBean(bean)));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public Object initializeBean(Class<?> clazz) {
		Class<?> concreteBeanClass = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
		return Optional.ofNullable(getBean(concreteBeanClass))
			.orElseGet((ExceptionWrapSuplier) () -> newInstanceBean(concreteBeanClass));
	}

	private <T> Object newInstanceBean(Class<T> concreteBeanClass) {
		return Optional.ofNullable(BeanFactoryUtils.getInjectedConstructor(concreteBeanClass))
			.map((ExceptionWrapFunction<Constructor, Object>) constructor -> newInstanceByConstructor(constructor))
			.orElseGet((ExceptionWrapSuplier) () -> concreteBeanClass.newInstance());
	}

	private <T> Object newInstanceByConstructor(Constructor<T> injectedConstructor) {
		try {
			Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
			List<Object> parameters = Arrays.stream(parameterTypes).map(this::initializeBean).collect(Collectors.toList());
			return injectedConstructor.newInstance(parameters.toArray());
		} catch (Exception e){
			logger.error("failed to create bean by constructor, {}", injectedConstructor);
			throw new RuntimeException(e.getMessage());
		}
	}
}
