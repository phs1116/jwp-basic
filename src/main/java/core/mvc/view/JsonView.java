package core.mvc.view;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by hspark on 2018. 4. 30..
 */
public class JsonView implements View {

	@Override
	public void render(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		resp.setContentType("application/json;charset=UTF-8");
		PrintWriter printWriter = resp.getWriter();
		printWriter.print(objectMapper.writeValueAsString(model));
	}
}
