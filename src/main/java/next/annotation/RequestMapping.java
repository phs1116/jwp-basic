package next.annotation;

import next.RequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by hspark on 2018. 4. 18..
 */

/**
 * http://hamait.tistory.com/314
 * @RequestMapping 스프링 참조
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface RequestMapping {
	String value() default "/";
	RequestMethod method() default RequestMethod.GET;
}
