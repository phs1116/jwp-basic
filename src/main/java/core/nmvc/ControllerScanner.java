package core.nmvc;

import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2018. 5. 6..
 */
public class ControllerScanner {

	private static final Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

	private Reflections reflections;

	public ControllerScanner(Object... params) {
		reflections = new Reflections(params);
	}

	public Map<Class<?>, Object> getControllers() {
		Set<Class<?>> controllerClassSet = reflections.getTypesAnnotatedWith(Controller.class);
		return controllerClassSet.stream().collect(Collectors.toMap(Function.identity(), (clazz) -> newInstanceByClass(clazz)));
	}

	//익셉션 처리
	private Object newInstanceByClass(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new BeanCreationException(e.getMessage());
		}
	}
}
