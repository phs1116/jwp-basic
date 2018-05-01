package core.mvc;

import com.google.common.collect.Maps;
import core.mvc.view.JsonView;
import core.mvc.view.JspView;
import core.mvc.view.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by hspark on 2018. 4. 30..
 */
public class ModelAndView {
	private Map<String, Object> model = Maps.newHashMap();
	private View view;

	private ModelAndView(View view) {
		this.view = view;
	}

	public static ModelAndView newJsonModelAndView(){
		return new ModelAndView(new JsonView());
	}

	public static ModelAndView newJspModelAndView(String viewName){
		return new ModelAndView(new JspView(viewName));
	}

	public void render(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		view.render(model, req, resp);
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public ModelAndView setModelAttribute(String key, Object value) {
		model.put(key, value);
		return this;
	}

	public Object getModelAttriute(String key) {
		return model.get(key);
	}


}
