package next.service;

import next.CannotDeleteException;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.List;

import static next.model.UserTest.newUser;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hspark on 2018. 5. 7..
 */
@RunWith(MockitoJUnitRunner.class)
public class QnaServiceTest {
	private QnaService qnaService;
	@Mock
	private QuestionDao questionDao;
	@Mock
	private AnswerDao answerDao;

	@Before
	public void setUp() {
		qnaService = new QnaService(questionDao, answerDao);
	}

	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_없는_질문() throws Exception {
		when(questionDao.findById(1L)).thenReturn(null);
		qnaService.deleteQuestion(1L, newUser("userId"));
	}

	@Test
	public void deleteQuestion_삭제할수_있음() throws Exception {
		User user = newUser("userId");
		Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
			public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
				return true;
			};
		};

		when(questionDao.findById(1L)).thenReturn(question);
		qnaService.deleteQuestion(1L, newUser("userId"));
		verify(questionDao).delete(1l);
	}

	@Test(expected = CannotDeleteException.class)
	public void deleteQuestion_삭제할수_없음() throws Exception {
		User user = newUser("userId");
		Question question = new Question(1L, user.getUserId(), "title", "contents", new Date(), 0) {
			public boolean canDelete(User user, List<Answer> answers) throws CannotDeleteException {
				throw new CannotDeleteException("삭제할 수 없음");
			};
		};

		when(questionDao.findById(1L)).thenReturn(question);
		qnaService.deleteQuestion(1L, newUser("user"));
	}

}