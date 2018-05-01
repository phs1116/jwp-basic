package next.controller.user;

import core.mvc.Controller;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        if (!UserSessionUtils.isLogined(req.getSession())) {
            return ModelAndView.newJspModelAndView("redirect:/users/loginForm");
        }

        UserDao userDao = new UserDao();
        return ModelAndView.newJspModelAndView("/user/list.jsp").setModelAttribute("users", userDao.findAll());
    }
}
