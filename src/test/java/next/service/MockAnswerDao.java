package next.service;

import com.google.common.collect.Maps;
import next.dao.AnswerDao;
import next.model.Answer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by hspark on 2018. 5. 7..
 */
public class MockAnswerDao implements AnswerDao {
	private Map<Long, Answer> answerHashMap = Maps.newConcurrentMap();
	@Override
	public Answer insert(Answer answer) {
		answerHashMap.put(answer.getAnswerId(), answer);
		return answerHashMap.get(answer.getAnswerId());
	}

	@Override
	public Answer findById(long answerId) {
		return answerHashMap.get(answerId);
	}

	@Override
	public List<Answer> findAllByQuestionId(long questionId) {
		return answerHashMap.values().stream()
			.filter(answer -> answer.getQuestionId() == questionId)
			.collect(Collectors.toList());
	}

	@Override
	public void delete(Long answerId) {
		answerHashMap.remove(answerId);
	}
}
