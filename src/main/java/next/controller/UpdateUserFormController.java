package next.controller;

import core.db.DataBase;
import next.RequestMethod;
import next.annotation.RequestMapping;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 4. 18..
 */


@RequestMapping(value = "/users/updateForm", method = RequestMethod.GET)
public class UpdateUserFormController implements Controller {
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userId");
		User user = DataBase.findUserById(userId);
		if (!UserSessionUtils.isSameUser(request.getSession(), user)) {
			throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
		}
		request.setAttribute("user", user);
		return "/user/updateForm.jsp";
	}
}
