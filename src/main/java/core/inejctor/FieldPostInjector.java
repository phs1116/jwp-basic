package core.inejctor;

import core.annotation.Inject;
import core.di.factory.BeanFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created by hspark on 2018. 5. 12..
 */
public class FieldPostInjector implements ConstructorPreInjector {

	private BeanFactory beanFactory;

	public FieldPostInjector(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	@Override
	public void postInject(Class<?> clazz) {
		Object injectedBean = getBeanFactory().getBean(clazz);

		if(Objects.isNull(injectedBean)){
			return;
		}

		Arrays.stream(clazz.getDeclaredFields())
			.filter(field -> field.isAnnotationPresent(Inject.class))
			.peek(field -> field.setAccessible(true))
			.forEach(field -> injectByField(injectedBean, field));
	}

	private void injectByField(Object injectedObj, Field field) {
		try {
			Object fieldObj = getBean(field.getType());
			field.set(injectedObj, getBeanFactory().getBean(field.getType()));
		} catch (Exception e){
			logger.error("failed to inject bean by field, bean : {}, field : {}", injectedObj, field);
			throw new RuntimeException(e.getMessage());
		}
	}

}
