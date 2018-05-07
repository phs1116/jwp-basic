package core.nmvc;

import core.mvc.LegacyHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by hspark on 2018. 5. 7..
 */
public class RequestHandler {
	private List<HandlerMapping> handlerMappingList;

	public RequestHandler() {
		LegacyHandlerMapping legacyHandlerMapping = new LegacyHandlerMapping();
		legacyHandlerMapping.initMapping();

		AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("next.controller");
		annotationHandlerMapping.initialize();

		handlerMappingList = Arrays.asList(legacyHandlerMapping, annotationHandlerMapping);
	}

	public Optional<?> handle(HttpServletRequest request){
		return handlerMappingList.stream()
			.map(handlerMapping -> handlerMapping.getHandler(request))
			.filter(Objects::nonNull).findAny();
	}
}
