package core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Created by hspark on 2018. 5. 11..
 */
public interface ExceptionWrapConsumer<T> extends Consumer<T> {

	Logger logger = LoggerFactory.getLogger(ExceptionWrapConsumer.class);

	@Override
	default void accept(T t) {
		try {
			acceptThrow(t);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	void acceptThrow(T t) throws Exception;
}
