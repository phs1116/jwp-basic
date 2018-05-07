package next.controller;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/")
public class HomeController {
    private QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) {
        return ModelAndView.newInstanceByJspView("home.jsp").addObject("questions", questionDao.findAll());
    }
}
