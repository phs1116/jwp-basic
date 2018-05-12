package core.inejctor;

import core.di.factory.BeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hspark on 2018. 5. 12..
 */
public interface Injector {
	Logger logger = LoggerFactory.getLogger(Injector.class);

	BeanFactory getBeanFactory();

	default void inject(Class<?> clazz){
		preInject(clazz);
		postInject(clazz);
	}

	void preInject(Class<?> clazz);
	void postInject(Class<?> clazz);
}
