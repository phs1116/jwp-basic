package next.dao;

/**
 * Created by hspark on 2018. 4. 29..
 */
public class DataAccessException extends RuntimeException {
	public DataAccessException() {
	}

	public DataAccessException(String message) {
		super(message);
	}
}
