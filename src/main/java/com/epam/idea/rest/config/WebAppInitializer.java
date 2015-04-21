package com.epam.idea.rest.config;

import javax.servlet.Filter;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Replacement for most of the content of web.xml, sets up the root and the servlet context config.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{RootContextConfiguration.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{RestServletContextConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[]{"/"};
	}

	@Override
	protected Filter[] getServletFilters() {
		final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		encodingFilter.setForceEncoding(true);
		return new Filter[]{encodingFilter};
	}
}
