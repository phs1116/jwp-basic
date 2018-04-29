package next.dao;

import com.google.common.collect.Lists;
import core.jdbc.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hspark on 2018. 4. 29..
 */
public class JdbcTemplate<T> {

	public void update(String query, Object... parameters) {
		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			setValues(pstmt, parameters);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public List<T> query(String query, RowMapper<T> rowMapper, Object... parameters) {
		List<T> objectList = Lists.newArrayList();
		try (Connection conn = ConnectionManager.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			setValues(pstmt, parameters);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					objectList.add(rowMapper.mapRow(rs));
				}
			}
			return objectList;
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
	}

	public T queryForObject(String query, RowMapper<T> rowMapper, Object... parameters) {
		try (Connection con = ConnectionManager.getConnection(); PreparedStatement pstmt = con.prepareStatement(query)) {
			setValues(pstmt, parameters);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rowMapper.mapRow(rs);
				}
			}
		} catch (SQLException e) {
			throw new DataAccessException(e.getMessage());
		}
		return null;
	}

	private void setValues(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
		for(int i = 0; i < parameters.length; i++){
			preparedStatement.setObject(i+1, parameters[i]);
		}
	}
}
