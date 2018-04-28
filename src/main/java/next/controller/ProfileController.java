package next.controller;

import core.db.DataBase;
import next.RequestMethod;
import next.annotation.RequestMapping;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequestMapping(value = "/users/profile", method = RequestMethod.GET)
public class ProfileController implements Controller {


    public String execute(HttpServletRequest request, HttpServletResponse response) {
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        request.setAttribute("user", user);
        return "/user/profile.jsp";
    }
}
