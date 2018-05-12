package core.inejctor;

import core.common.ExceptionWrapFunction;
import core.common.ExceptionWrapSupplier;
import core.di.factory.BeanFactoryUtils;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2018. 5. 12..
 */
public interface ConstructorPreInjector extends Injector {

	@Override
	default void preInject(Class<?> clazz) {
		Class<?> concreteBeanClass = BeanFactoryUtils.findConcreteClass(clazz, getBeanFactory().getPreInstanticateBeans());
		Object obj = Optional.ofNullable(getBeanFactory().getBean(concreteBeanClass))
			.orElseGet((ExceptionWrapSupplier) () -> newInstanceBean(concreteBeanClass));
		getBeanFactory().putBean(clazz, obj);
	}

	default <T> Object newInstanceBean(Class<T> concreteBeanClass) {
		return Optional.ofNullable(BeanFactoryUtils.getInjectedConstructor(concreteBeanClass))
			.map((ExceptionWrapFunction<Constructor, Object>) constructor -> newInstanceByConstructor(constructor))
			.orElseGet((ExceptionWrapSupplier) () -> concreteBeanClass.newInstance());
	}

	default <T> Object newInstanceByConstructor(Constructor<T> injectedConstructor) {
		try {
			Class<?>[] parameterTypes = injectedConstructor.getParameterTypes();
			List<Object> parameters = Arrays.stream(parameterTypes).peek(this::preInject)
				.map(clazz -> getBeanFactory().getBean(clazz)).collect(Collectors.toList());

			return injectedConstructor.newInstance(parameters.toArray());
		} catch (Exception e) {
			logger.error("failed to create bean by constructor, {}", injectedConstructor);
			throw new RuntimeException(e.getMessage());
		}
	}

	void postInject(Class<?> clazz);

	default Object getBean(Class<?> clazz) {
		Object fieldObj = getBeanFactory().getBean(clazz);
		if (Objects.isNull(fieldObj)) {
			inject(clazz);
		}
		return getBeanFactory().getBean(clazz);
	}
}
