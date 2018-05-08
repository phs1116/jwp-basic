package next.dao;

import next.model.Answer;

import java.util.List;

/**
 * Created by coupang on 2018. 5. 7..
 */
public interface AnswerDao {
	Answer insert(Answer answer);

	Answer findById(long answerId);

	List<Answer> findAllByQuestionId(long questionId);

	void delete(Long answerId);
}
