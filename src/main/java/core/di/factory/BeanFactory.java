package core.di.factory;

import com.google.common.collect.Maps;
import core.common.ExceptionWrapConsumer;
import core.di.bean.BeanDefinition;
import core.di.bean.BeanDefinitionRegistry;
import core.di.bean.InjectType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory implements BeanDefinitionRegistry {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	private Map<Class<?>, BeanDefinition> beanDefinitions = Maps.newHashMap();

	@SuppressWarnings("unchecked")
	public void putBean(Class<?> type, Object object) {
		beans.put(type, object);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		T  bean = requiredType.cast(beans.get(requiredType));

		if(Objects.nonNull(bean)){
			return bean;
		}

		Class<?> concreteBeanClass = BeanFactoryUtils.findConcreteClass(requiredType, getBeanClasses());
		BeanDefinition beanDefinition = beanDefinitions.get(concreteBeanClass);
		return requiredType.cast(beans.put(concreteBeanClass, inject(beanDefinition)));
	}

	public Set<Class<?>> getBeanClasses() {
		return beanDefinitions.keySet();
	}

	public void initialize() {
		try {
			beanDefinitions.entrySet().stream().forEach(entry -> getBean(entry.getKey()));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	private Object inject(BeanDefinition beanDefinition) {
		InjectType injectType = beanDefinition.getInjectType();
		if(injectType == InjectType.CONSTRUCTOR) {
			return injectByConstructor(beanDefinition);
		} else if(injectType == InjectType.FIELD) {
			Object bean = injectByFields(beanDefinition);
			return bean;
		} else {
			return BeanUtils.instantiate(beanDefinition.getBeanClass());
		}
	}

	private Object injectByFields(BeanDefinition beanDefinition) {
		Set<Field> fields = beanDefinition.getInjectFields();
		Object bean = BeanUtils.instantiate(beanDefinition.getBeanClass());
		fields.stream().peek(field -> logger.debug("inject field, class : {}, field : {}", beanDefinition.getBeanClass(), field))
			.peek(field -> field.setAccessible(true))
			.forEach((ExceptionWrapConsumer<Field>) field -> field.set(bean, getBean(field.getType())));
		return bean;
	}

	private Object injectByConstructor(BeanDefinition beanDefinition) {
		Constructor<?> constructor = beanDefinition.getInjectConstructor();
		logger.debug("inject by constructor, class : {}, constructor : {}", beanDefinition.getBeanClass(), constructor);
		Class<?>[] parameterTypes = constructor.getParameterTypes();
		List<Object> parameters = Arrays.stream(parameterTypes)
			.map(this::getBean).collect(Collectors.toList());
		return BeanUtils.instantiateClass(constructor, parameters.toArray());
	}

	@Override
	public void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition) {
		logger.debug("register bean : {}", clazz);
		beanDefinitions.put(clazz, beanDefinition);
	}
}
