package core.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by hspark on 2018. 4. 18..
 */
public class ContextHolderFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {




		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {

	}
}
