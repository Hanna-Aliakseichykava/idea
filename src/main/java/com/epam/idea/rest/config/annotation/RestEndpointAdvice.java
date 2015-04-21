package com.epam.idea.rest.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * {@code @RestEndpointAdvice} is a class-level annotation same as
 * {@code @ControllerAdvice} but specific to RESTful endpoint controllers.
 *
 * @see org.springframework.web.bind.annotation.ControllerAdvice
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
public @interface RestEndpointAdvice {

	/**
	 * Serve the same purpose here as it does in other stereotype annotations:
	 * It provides a way to specify the bean name that overrides the default
	 * bean name pattern.
	 */
	String value() default "";

}
