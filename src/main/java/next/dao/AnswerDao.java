package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hspark on 2018. 4. 29..
 */
public class AnswerDao {
	private static final String SELECT_BY_QUESTION_QUERY = "SELECT * FROM ANSWERS WHERE questionId = ?";

	public List<Answer> findByQuestionId(Long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		return jdbcTemplate.query(SELECT_BY_QUESTION_QUERY, this::getAnswer, questionId);
	}

	private Answer getAnswer(ResultSet rs) throws SQLException {
		Answer answer = new Answer();
		answer.setAnswerId(rs.getLong("answerId"));
		answer.setContents(rs.getString("contents"));
		answer.setWriter(rs.getString("writer"));
		answer.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
		return answer;
	}
}
