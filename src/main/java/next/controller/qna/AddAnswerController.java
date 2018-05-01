package next.controller.qna;

import core.mvc.Controller;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Answer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 4. 30..
 */
public class AddAnswerController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Answer answer = new Answer(req.getParameter("writer"), req.getParameter("contents"), Long.parseLong(req.getParameter("questionId")));

		AnswerDao answerDao = new AnswerDao();
		Answer savedAnswer = answerDao.insert(answer);

		return ModelAndView.newJsonModelAndView().setModelAttribute("savedAnswer", savedAnswer);
	}
}
