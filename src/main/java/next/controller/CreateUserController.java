package next.controller;

import core.db.DataBase;
import next.RequestMethod;
import next.annotation.RequestMapping;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hspark on 2018. 4. 18..
 */

@RequestMapping(value = "/users/create", method = RequestMethod.POST)
public class CreateUserController implements next.controller.Controller {
	private static final Logger log = LoggerFactory.getLogger(CreateUserController.class);

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		User user = new User(request.getParameter("userId"), request.getParameter("password"), request.getParameter("name"),
			request.getParameter("email"));
		log.debug("User : {}", user);

		DataBase.addUser(user);

		return "redirect:/";
	}
}


