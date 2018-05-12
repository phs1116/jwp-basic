package core.di.factory;

import core.annotation.Controller;
import core.annotation.Repository;
import core.annotation.Service;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2018. 5. 11..
 */
public class BeanScanner {
	private static final Logger log = LoggerFactory.getLogger(BeanScanner.class);

	public static final List<Class<? extends Annotation>> TARGET_ANNOTATIONS =
		Arrays.asList(Controller.class, Service.class, Repository.class);

	private Reflections reflections;

	public BeanScanner(Object... basePackage) {
		reflections = new Reflections(basePackage);
	}

	public Set<Class<?>> scan() {
		return TARGET_ANNOTATIONS.stream().flatMap(aClass -> reflections.getTypesAnnotatedWith(aClass).stream())
			.collect(Collectors.toSet());
	}
}
