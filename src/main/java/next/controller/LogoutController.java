package next.controller;

import next.RequestMethod;
import next.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@RequestMapping(value = "/users/logout", method = RequestMethod.GET)
public class LogoutController implements Controller{

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }
}
