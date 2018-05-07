package core.nmvc;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 5. 7..
 */
public class AnnotationHandlerAdapter implements HandlerAdapter<HandlerExecution>{

	@Override
	public ModelAndView execute(HandlerExecution handlerExecution, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return handlerExecution.handle(request, response);
	}

	@Override
	public boolean isSupported(Object obj) {
		return HandlerExecution.class.isInstance(obj);
	}
}
