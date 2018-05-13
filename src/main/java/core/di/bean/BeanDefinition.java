package core.di.bean;

import core.annotation.Inject;
import core.di.factory.BeanFactoryUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2018. 5. 13..
 */


public class BeanDefinition {
	private Class<?> beanClazz;
	private Constructor<?> injectConstructor;
	private Set<Field> injectFields;

	private static final String SETTER_PREFIX = "set";
	private static final int SETTER_PARAMETER_COUNT = 1;

	public BeanDefinition(Class<?> clazz) {
		this.beanClazz = clazz;
		this.injectConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
		this.injectFields = getInjectFields(clazz);
	}

	private Set<Field> getInjectFields(Class<?> clazz) {
		Set<Class<?>> injectProperties = getInjectPropertiesType(clazz);
		return Arrays.stream(clazz.getDeclaredFields()).filter(field -> injectProperties.contains(field.getType()))
			.collect(Collectors.toSet());
	}

	private Set<Class<?>> getInjectPropertiesType(Class<?> clazz) {

		Set<Class<?>> injectProperties =  Arrays.stream(clazz.getDeclaredMethods())
			.filter(method -> method.isAnnotationPresent(Inject.class))
			.filter(method -> method.getName().startsWith(SETTER_PREFIX))
			.filter(method -> method.getParameterCount() == SETTER_PARAMETER_COUNT)
			.flatMap(method -> Arrays.stream(method.getParameterTypes())).collect(Collectors.toSet());

		Set<Class<?>> injectFieldsProperties = Arrays.stream(clazz.getDeclaredFields())
			.filter(field -> field.isAnnotationPresent(Inject.class)).map(field -> field.getType())
			.collect(Collectors.toSet());

		injectProperties.addAll(injectFieldsProperties);

		return injectProperties;
	}

	public Class<?> getBeanClass() {
		return beanClazz;
	}

	public Constructor<?> getInjectConstructor() {
		return injectConstructor;
	}

	public Set<Field> getInjectFields() {
		return injectFields;
	}

	public InjectType getInjectType(){
		return Objects.nonNull(injectConstructor) ? InjectType.CONSTRUCTOR :
			(Objects.nonNull(injectFields) ? InjectType.FIELD : InjectType.NONE);
	}
}
