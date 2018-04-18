package next.web;

import core.db.DataBase;
import next.model.User;

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
@WebServlet("/user/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		User user = DataBase.findUserById(userId);

		if(Objects.nonNull(password) && Objects.equals(user.getPassword(), password)){
			HttpSession session = req.getSession();
			session.setAttribute("user", user);
		}
		resp.sendRedirect("/index.jsp");
	}
}
