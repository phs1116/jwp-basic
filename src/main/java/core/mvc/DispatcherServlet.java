package core.mvc;

import common.ThrowingExceptionConsumer;
import common.ThrowingExceptionFunction;
import core.nmvc.HandlerAdapter;
import core.nmvc.HandlerAdapterFactory;
import core.nmvc.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private RequestHandler requestHandler;
	private HandlerAdapterFactory handlerAdapterFactory;

    @Override
    public void init() {
    	requestHandler = new RequestHandler();
    	handlerAdapterFactory = new HandlerAdapterFactory();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        try {
        	Optional<?> optional =  requestHandler.handle(req);

			optional.map((ThrowingExceptionFunction<Object, ModelAndView>) handler-> {
				HandlerAdapter handlerAdapter = handlerAdapterFactory.getInstance(handler);
				return handlerAdapter.execute(handler, req, resp);
			}).ifPresent((ThrowingExceptionConsumer<ModelAndView>) (ModelAndView modelAndView)-> modelAndView.render(req, resp));

		}catch (Throwable e){
        	throw new ServletException(e.getMessage());
		}
    }

}
