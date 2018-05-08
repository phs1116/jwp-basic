package next.dao;

import next.model.Question;

import java.util.List;

/**
 * Created by hspark on 2018. 5. 7..
 */
public interface QuestionDao {
	Question insert(Question question);

	List<Question> findAll();

	Question findById(long questionId);

	void update(Question question);

	void delete(long questionId);

	void updateCountOfAnswer(long questionId);
}
