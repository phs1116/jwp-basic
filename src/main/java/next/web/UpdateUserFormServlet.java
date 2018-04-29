package next.web;

import core.db.DataBase;
import next.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by hspark on 2018. 4. 17..
 */

@WebServlet("/user/update")
public class UpdateUserFormServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		if (Objects.isNull(user)) {
			resp.sendRedirect("/index.jsp");
			return;
		}
		req.setAttribute("user", user);
		RequestDispatcher requestDispatcher = req.getRequestDispatcher("/user/update.jsp");
		requestDispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = DataBase.findUserById(req.getParameter("userId"));
		HttpSession session = req.getSession();
		User loginedUser = (User) session.getAttribute("user");
		if (Objects.isNull(loginedUser)) {
			resp.sendRedirect("/user/login");
			return;
		}

		if (Objects.isNull(user) || !loginedUser.equals(user)) {
			resp.sendRedirect("/index.jsp");
			return;
		}

		user.setPassword(req.getParameter("password"));
		user.setEmail(req.getParameter("email"));
		user.setName(req.getParameter("name"));
		DataBase.addUser(user);

		resp.sendRedirect("/user/list");
	}
}
