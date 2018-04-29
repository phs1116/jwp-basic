package next.dao;

import next.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao {
	public static final String UPDATE_QUERY = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";
	public static final String INSERT_QUERY = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
	public static final String SELECT_QUERY = "SELECT userId, password, name, email FROM USERS WHERE userId=?";
	public static final String ALL_SELECT_QUERY = "SELECT userId, password, name, email FROM USERS";

	public void insert(User user) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.update(INSERT_QUERY, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
	}

	public void update(User user) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.update(UPDATE_QUERY,  user.getPassword(),user.getName(), user.getEmail(), user.getUserId());
	}

	public List<User> findAll() {
		JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();
		return jdbcTemplate.query(ALL_SELECT_QUERY, this::getUser);
	}

	public User findByUserId(String userId) {
		JdbcTemplate<User> jdbcTemplate = new JdbcTemplate<>();
		return jdbcTemplate.queryForObject(SELECT_QUERY, this::getUser, userId);
	}

	private User getUser(ResultSet rs) throws SQLException {
		return new User(rs.getString("userId"),
			rs.getString("password"),
			rs.getString("name"),
			rs.getString("email"));
	}
}
