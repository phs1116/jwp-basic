package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Created by hspark on 2018. 5. 1..
 */
public class CreateQuestionFormController extends AbstractController {
	private UserDao userDao = new UserDao();

	@Override
	public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (!UserSessionUtils.isLogined(request.getSession())) {
			return jspView("redirect:/");
		}

		User sessionUser = UserSessionUtils.getUserFromSession(request.getSession());
		if (Objects.isNull(sessionUser)) {
			return jspView("redirect:/");
		}

		User existsUser = userDao.findByUserId(sessionUser.getUserId());
		if (Objects.isNull(sessionUser) || Objects.isNull(existsUser)) {
			return jspView("redirect:/");
		}

		return jspView("/qna/form.jsp").addObject("user", existsUser);
	}
}
