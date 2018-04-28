package next.dispatcher;

import next.controller.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by hspark on 2018. 4. 18..
 */
@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final String REDIRECT_PREFIX = "redirect:";

	private RequestMapper requestMapper;

	@Override
	public void init() throws ServletException {
		requestMapper = new RequestMapper();
		super.init();
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info(req.toString());
		Controller controller = requestMapper.getController(req);
		String viewName = controller.execute(req, resp);
		if (viewName.startsWith(REDIRECT_PREFIX)) {
			resp.sendRedirect(viewName.substring(REDIRECT_PREFIX.length()));
			return;
		}
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(viewName);
		requestDispatcher.forward(req, resp);
	}

}
