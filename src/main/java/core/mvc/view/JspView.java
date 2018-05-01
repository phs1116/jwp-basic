package core.mvc.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hspark on 2018. 4. 30..
 */
public class JspView implements View {
	private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";
	private String viewName;

	public JspView(String viewName) {
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	public void render(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		if(viewName == null){
			return;
		}

		model.entrySet().stream().forEach(entry->{
			req.setAttribute(entry.getKey(), entry.getValue());
		});

		if (viewName.startsWith(DEFAULT_REDIRECT_PREFIX)) {
			resp.sendRedirect(viewName.substring(DEFAULT_REDIRECT_PREFIX.length()));
			return;
		}

		RequestDispatcher rd = req.getRequestDispatcher(viewName);
		rd.forward(req, resp);
	}
}
