package next.service;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Answer;
import next.model.Question;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by hspark on 2018. 5. 1..
 */
public class QuestionServiceImpl implements QuestionService{
	private QuestionDao questionDao = new QuestionDao();
	private AnswerDao answerDao = new AnswerDao();
	private UserDao userDao = new UserDao();

	@Override
	public void deleteQuestion(Long questionId) {
		Question question = questionDao.findById(questionId);
		List<Answer> answerList = answerDao.findAllByQuestionId(questionId);
		String questionWriter = question.getWriter();
		if(answerList.isEmpty() || answerList.stream().allMatch(answer -> StringUtils.equals(answer.getWriter(), questionWriter))){
			questionDao.delete(questionId);
			answerDao.deleteByQuestionId(questionId);
		}
	}
}
