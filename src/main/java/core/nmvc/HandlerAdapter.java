package core.nmvc;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 5. 7..
 */

/**
 * Adapter Pattern
 */
public interface HandlerAdapter<T> {
	ModelAndView execute(T obj, HttpServletRequest request, HttpServletResponse response) throws Exception;
	boolean isSupported(Object obj);
}
