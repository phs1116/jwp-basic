package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

/**
 * Created by hspark on 2018. 5. 7..
 */
public interface ThrowingExceptionConsumer<T> extends Consumer<T> {
	Logger logger = LoggerFactory.getLogger(ThrowingExceptionConsumer.class);

	@Override
	default void accept(T t){
		try {
			acceptThrow(t);
		} catch (Exception e) {
			logger.error("Exception : {}", e.getMessage());
			throw new RuntimeException(e);
		}

	}

	void acceptThrow(T t) throws Exception;
}
