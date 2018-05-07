package common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

/**
 * Created by hspark on 2018. 5. 7..
 */
@FunctionalInterface
public interface ThrowingExceptionFunction<T, R> extends Function<T, R> {
	Logger logger = LoggerFactory.getLogger(ThrowingExceptionFunction.class);

	@Override
	default R apply(T t) {
		try {
			return applyThrow(t);
		} catch (Exception e) {
			logger.error("Exception : {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	R applyThrow(T t) throws Exception;
}