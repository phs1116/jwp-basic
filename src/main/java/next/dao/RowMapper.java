package next.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by hspark on 2018. 4. 29..
 */
@FunctionalInterface
public interface RowMapper<T> {
	T mapRow(ResultSet resultSet) throws SQLException;
}
