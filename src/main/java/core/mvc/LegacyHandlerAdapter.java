package core.mvc;

import core.nmvc.HandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 5. 7..
 */
public class LegacyHandlerAdapter implements HandlerAdapter<Controller> {
	@Override
	public ModelAndView execute(Controller controller, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return controller.execute(request,response);
	}

	@Override
	public boolean isSupported(Object obj) {
		return Controller.class.isInstance(obj);
	}
}
