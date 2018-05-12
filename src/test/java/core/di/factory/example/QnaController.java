package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;
import core.annotation.RequestMapping;
import core.web.mvc.AbstractNewController;
import core.web.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class QnaController extends AbstractNewController {
    @Inject
    private MyQnaService qnaService;

    public MyQnaService getQnaService() {
        return qnaService;
    }

    @RequestMapping("/questions")
    public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return jspView("/qna/list.jsp");
    }
}
