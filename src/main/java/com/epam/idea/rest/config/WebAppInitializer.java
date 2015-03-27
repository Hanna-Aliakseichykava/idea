package com.epam.idea.rest.config;

import java.io.IOException;
import javax.servlet.Filter;

import com.epam.idea.core.repository.config.support.DatabaseConfigProfile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Replacement for most of the content of web.xml, sets up the root and the servlet context config.
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{WebAppConfig.class};
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

	@Override
	protected WebApplicationContext createRootApplicationContext() {
		Class<?>[] configClasses = getRootConfigClasses();
		if (!ObjectUtils.isEmpty(configClasses)) {
			AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
			rootAppContext.register(configClasses);
			ConfigurableEnvironment environment = rootAppContext.getEnvironment();
			try {
				environment.getPropertySources().addFirst(new ResourcePropertySource("classpath:application.properties"));
				environment.setActiveProfiles(environment.getRequiredProperty("spring.profiles.active"));
				//todo add logs
			} catch (IOException | IllegalStateException e) {
				// it's ok if the file is not there or required property is absent. set default profile.
				environment.setActiveProfiles(DatabaseConfigProfile.DEV);
			}
			return rootAppContext;
		}
		else {
			return null;
		}
	}
}
