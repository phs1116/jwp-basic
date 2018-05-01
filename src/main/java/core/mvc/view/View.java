package core.mvc.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hspark on 2018. 4. 30..
 */
public interface View {
	public void render(Map<String,Object> model, HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
