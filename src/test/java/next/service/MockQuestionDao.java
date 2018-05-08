package next.service;

import com.google.common.collect.Maps;
import next.dao.QuestionDao;
import next.model.Question;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2018. 5. 7..
 */
public class MockQuestionDao implements QuestionDao {
	private Map<Long, Question> questionMap = Maps.newConcurrentMap();
	@Override
	public Question insert(Question question) {
		return questionMap.put(question.getQuestionId(), question);
	}

	@Override
	public List<Question> findAll() {
		return questionMap.values().stream().collect(Collectors.toList());
	}

	@Override
	public Question findById(long questionId) {
		return questionMap.get(questionId);
	}

	@Override
	public void update(Question question) {
		questionMap.put(question.getQuestionId(), question);
	}

	@Override
	public void delete(long questionId) {
		questionMap.remove(questionId);
	}

	@Override
	public void updateCountOfAnswer(long questionId) {
		Question question = questionMap.get(questionId);
		question.setCountOfComment(question.getCountOfComment()+1);
	}
}
