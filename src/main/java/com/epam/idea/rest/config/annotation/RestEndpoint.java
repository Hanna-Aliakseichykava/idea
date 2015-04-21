package com.epam.idea.rest.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;

/**
 * {@code @RestEndpoint} is a class-level annotation practically identical to {@code @Controller},
 * but their semantic meanings have important differences. Both indicate that the target bean is a
 * controller eligible for request mapping. However, {@code @Controller} marks controllers for
 * traditional web requests, whereas @RestEndpoint denotes RESTful web service endpoints.
 *
 * @see org.springframework.stereotype.Controller
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
public @interface RestEndpoint {

	/**
	 * Serve the same purpose here as it does in other stereotype annotations:
	 * It provides a way to specify the bean name that overrides the default
	 * bean name pattern.
	 */
	String value() default "";

}
