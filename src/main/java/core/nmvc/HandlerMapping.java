package core.nmvc;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hspark on 2018. 5. 7..
 */
public interface HandlerMapping {
	Object getHandler(HttpServletRequest request);
}
