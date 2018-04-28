package next.dispatcher;

import com.google.common.collect.Maps;
import next.RequestMethod;
import next.controller.Controller;
import next.controller.CreateUserController;
import next.controller.HomeController;
import next.controller.ListUserController;
import next.controller.LoginController;
import next.controller.UpdateUserController;
import next.controller.UpdateUserFormController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by hspark on 2018. 4. 18..
 */
public class RequestMapper {

	private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);
	private Map<RequestMethod, Map<String, Controller>> mapper = Maps.newConcurrentMap();

	public RequestMapper() {
		initMapper();
	}

	void initMapper() {
		Map<String, Controller> putMapper = mapper.put(RequestMethod.PUT, Maps.newConcurrentMap());
		Map<String, Controller> deleteMapper = mapper.put(RequestMethod.DELETE, Maps.newConcurrentMap());
		initGetMapper();
		initPostMapper();
		logger.info("complete init mapper");
	}

	private void initGetMapper() {
		Map<String, Controller> getMapper = mapper.computeIfAbsent(RequestMethod.GET, (map)->Maps.newConcurrentMap());
		getMapper.put("/users/form", (req, res) -> "/user/form.jsp");
		getMapper.put("/", new HomeController());
		getMapper.put("/users", new ListUserController());
		getMapper.put("/users/loginForm", (req, res) -> "/user/login.jsp");
		getMapper.put("/users/updateForm", new UpdateUserFormController());
		logger.info("init getMapper");
	}

	private void initPostMapper() {
		Map<String, Controller> postMapper = mapper.computeIfAbsent(RequestMethod.POST, (map)->Maps.newConcurrentMap());
		postMapper.put("/users/create", new CreateUserController());
		postMapper.put("/users/login", new LoginController());
		postMapper.put("/users/update", new UpdateUserController());
		logger.info("init postMapper");
	}

	public Controller getController(HttpServletRequest request) {
		RequestMethod requestMethod = RequestMethod.valueOf(request.getMethod());
		String url = request.getRequestURI();
		return mapper.get(requestMethod).get(url);
	}
}
