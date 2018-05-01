package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by hspark on 2018. 5. 1..
 */
public class CreateQuestionController extends AbstractController {
	private QuestionDao questionDao = new QuestionDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (!UserSessionUtils.isLogined(request.getSession())) {
			return jspView("redirect:/");
		}
		String writer = request.getParameter("writer");
		String title = request.getParameter("title");
		String contents = request.getParameter("contents");

		if (StringUtils.isEmpty(writer) || StringUtils.isEmpty(title) || StringUtils.isEmpty(contents)) {
			throw new IllegalArgumentException("입력 값이 올바르지 않습니다.");
		}

		Question question = new Question(writer, title, contents);
		Question savedQustion = questionDao.insert(question);

		if (Objects.isNull(savedQustion)) {
			jspView("redirect:/qna/form");
		}

		return jspView("redirect:/");
	}
}
