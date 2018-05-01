package next.controller.qna;

import core.mvc.Controller;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 4. 30..
 */
public class DeleteAnswerController implements Controller {

	@Override
	public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Long answerId = Long.valueOf(req.getParameter("answerId"));
		AnswerDao answerDao = new AnswerDao();
		answerDao.delete(answerId);
		return ModelAndView.newJsonModelAndView().setModelAttribute("result", Result.ok());
	}
}
