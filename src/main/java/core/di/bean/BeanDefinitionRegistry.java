package core.di.bean;

/**
 * Created by hspark on 2018. 5. 13..
 */
public interface BeanDefinitionRegistry {
	void registerBeanDefinition(Class<?> clazz, BeanDefinition beanDefinition);
}
