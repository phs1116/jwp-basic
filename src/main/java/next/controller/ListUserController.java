package next.controller;

import core.db.DataBase;
import next.RequestMethod;
import next.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequestMapping(value = "/users", method = RequestMethod.GET)
public class ListUserController implements Controller{
    private static final long serialVersionUID = 1L;

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (!UserSessionUtils.isLogined(request.getSession())) {
            return "redirect:/users/loginForm";
        }
        request.setAttribute("users", DataBase.findAll());
        return "/user/list.jsp";
    }
}
