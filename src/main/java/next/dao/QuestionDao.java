package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by hspark on 2018. 4. 29..
 */
public class QuestionDao {
	private static final String SELECT_QUERY = "SELECT * FROM QUESTIONS WHERE questionId = ?";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM QUESTIONS";

	public Question findByQuestionId(Long questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		return jdbcTemplate.queryForObject(SELECT_QUERY, this::getQuestion, questionId);
	}

	public List<Question> findAll(){
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		return jdbcTemplate.query(SELECT_ALL_QUERY, this::getQuestion);
	}

	private Question getQuestion(ResultSet rs) throws SQLException {
		Question question = new Question();
		question.setQuestionId(rs.getLong("questionId"));
		question.setWriter(rs.getString("writer"));
		question.setTitle(rs.getString("title"));
		question.setContents(rs.getString("contents"));
		question.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
		question.setCountOfAnswer(rs.getInt("countOfAnswer"));
		return question;
	}
}
