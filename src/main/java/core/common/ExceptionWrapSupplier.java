package core.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * Created by hspark on 2018. 5. 11..
 */
public interface ExceptionWrapSupplier<T> extends Supplier<T> {

	Logger logger = LoggerFactory.getLogger(ExceptionWrapSupplier.class);

	@Override
	default T get() {
		try {
			return getThrow();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
	}

	T getThrow() throws Exception;
}
