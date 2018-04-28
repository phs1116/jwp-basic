package next.controller;

import core.db.DataBase;
import next.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping
public class HomeController implements Controller {
    private static final long serialVersionUID = 1L;

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("users", DataBase.findAll());
        return "/index.jsp";
    }
}
