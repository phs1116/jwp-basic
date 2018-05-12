package core.di.factory;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.inejctor.FieldPostInjector;
import core.inejctor.Injector;
import core.inejctor.SetterPostInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class BeanFactory {
	private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

	private Set<Class<?>> preInstanticateBeans;

	private List<Injector> injectorList;

	private Map<Class<?>, Object> beans = Maps.newHashMap();

	public BeanFactory(Set<Class<?>> preInstanticateBeans) {
		this.preInstanticateBeans = preInstanticateBeans;
		this.injectorList = Arrays.asList(new FieldPostInjector(this), new SetterPostInjector(this));
	}

	public Set<Class<?>> getPreInstanticateBeans() {
		return preInstanticateBeans;
	}

	@SuppressWarnings("unchecked")
	public void putBean(Class<?> type, Object object) {
		beans.put(type, object);
	}

	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> requiredType) {
		return (T) beans.get(requiredType);
	}

	public Map<Class<?>, Object> getControllers() {
		return beans.entrySet().stream()
			.filter(entry -> entry.getKey().isAnnotationPresent(Controller.class))
			.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
	}

	public void initialize() {
		try {
			preInstanticateBeans.forEach(this::inject);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	private void inject(Class<?> clazz) {
		injectorList.forEach(injector -> injector.inject(clazz));
	}
}
