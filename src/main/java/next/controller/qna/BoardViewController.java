package next.controller.qna;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by hspark on 2018. 4. 29..
 */
public class BoardViewController implements Controller {
	private static final Logger log = LoggerFactory.getLogger(BoardViewController.class);
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long questionId = Long.valueOf(req.getParameter("questionId"));
		QuestionDao questionDao = new QuestionDao();
		Question question = questionDao.findByQuestionId(questionId);

		AnswerDao answerDao = new AnswerDao();
		List<Answer> answerList = answerDao.findByQuestionId(questionId);

		req.setAttribute("question", question);
		req.setAttribute("answerList", answerList);

		return "/qna/show.jsp";
	}
}
