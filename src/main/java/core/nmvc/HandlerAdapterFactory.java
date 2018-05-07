package core.nmvc;

import core.mvc.LegacyHandlerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hspark on 2018. 5. 7..
 */
public class HandlerAdapterFactory {
	private List<HandlerAdapter> handlerAdapterList;

	public HandlerAdapterFactory() {
		handlerAdapterList = Arrays.asList(new LegacyHandlerAdapter(), new AnnotationHandlerAdapter());
	}

	public HandlerAdapter getInstance(final Object obj) {
		return handlerAdapterList.stream().filter(handlerAdapter -> handlerAdapter.isSupported(obj))
			.findAny().orElseThrow(()->new IllegalArgumentException(String.format("올바르지 않은 Handler입니다., obj : %s", obj.toString())));
	}

}
