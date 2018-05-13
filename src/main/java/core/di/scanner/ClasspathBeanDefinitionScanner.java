package core.di.scanner;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import core.di.bean.BeanDefinition;
import core.di.bean.BeanDefinitionRegistry;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hspark on 2018. 5. 11..
 */
public class ClasspathBeanDefinitionScanner implements BeanScanner {
	private static final Logger log = LoggerFactory.getLogger(ClasspathBeanDefinitionScanner.class);

	public static final List<Class<? extends Annotation>> TARGET_ANNOTATIONS =
		Arrays.asList(Controller.class, Service.class, Repository.class);

	private final BeanDefinitionRegistry beanDefinitionRegistry;

	public ClasspathBeanDefinitionScanner(BeanDefinitionRegistry beanDefinitionRegistry) {
		this.beanDefinitionRegistry = beanDefinitionRegistry;
	}

	@Override
	public void doScan(Object... basePackages) {
		Reflections reflections = new Reflections(basePackages);
		TARGET_ANNOTATIONS.stream().flatMap(aClass -> reflections.getTypesAnnotatedWith(aClass).stream())
			.forEach(aClass -> beanDefinitionRegistry.registerBeanDefinition(aClass, new BeanDefinition(aClass)));
	}
}
