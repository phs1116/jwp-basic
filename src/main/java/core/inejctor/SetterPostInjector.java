package core.inejctor;

import core.annotation.Inject;
import core.di.factory.BeanFactory;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2018. 5. 12..
 */
public class SetterPostInjector implements ConstructorPreInjector {

	private BeanFactory beanFactory;

	public SetterPostInjector(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void postInject(Class<?> clazz) {
		Object injectedBean = getBeanFactory().getBean(clazz);
		if (Objects.isNull(injectedBean)) {
			return;
		}

		Arrays.stream(clazz.getDeclaredMethods())
			.filter(method -> method.isAnnotationPresent(Inject.class) && method.getName().startsWith("set"))
			.peek(method -> method.setAccessible(true)).forEach(method -> injectByMethod(injectedBean, method));
	}

	private void injectByMethod(Object injectedBean, Method method) {
		try {
			Class<?>[] parameterTypes = method.getParameterTypes();
			List<Object> parameter = Arrays.stream(parameterTypes)
				.map(parameterType -> getBean(parameterType)).collect(Collectors.toList());
			method.invoke(injectedBean, parameter.toArray());
		} catch (Exception e) {
			logger.error("failed to inject bean by field, bean : {}, method : {}", injectedBean, method);
			throw new RuntimeException(e.getMessage());
		}
	}
}
