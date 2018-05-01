package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.model.Result;
import next.service.QuestionService;
import next.service.QuestionServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 5. 1..
 */
public class DeleteQuestionApiController extends AbstractController {
	private QuestionService questionService = new QuestionServiceImpl();
	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Long questionId = Long.valueOf(request.getParameter("questionId"));
		try {
			questionService.deleteQuestion(questionId);
			return jsonView().addObject("result", Result.ok());
		}catch (Exception e){
			return jsonView().addObject("result", Result.fail(e.getMessage()));
		}
	}
}
