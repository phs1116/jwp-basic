package core.di;

import core.di.factory.BeanFactory;
import core.di.scanner.ClasspathBeanDefinitionScanner;

import java.util.Set;

/**
 * Created by hspark on 2018. 5. 13..
 */
public class ApplicationContext {
	private BeanFactory beanFactory;

	public ApplicationContext(Object... basePackages) {
		this.beanFactory = new BeanFactory();
		ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
		scanner.doScan(basePackages);
		beanFactory.initialize();
	}

	public <T> T getBean(Class<T> clazz){
		return beanFactory.getBean(clazz);
	}

	public Set<Class<?>> getBeanClasses(){
		return beanFactory.getBeanClasses();
	}
}
