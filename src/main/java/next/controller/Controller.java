package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 4. 28..
 */
public interface Controller {
	String execute(HttpServletRequest request, HttpServletResponse response);
}
