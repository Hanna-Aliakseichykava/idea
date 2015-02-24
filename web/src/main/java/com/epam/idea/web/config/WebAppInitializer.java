package com.epam.idea.web.config;

/**
 * Replacement for most of the content of web.xml, sets up the root and the servlet context config.
 */
//@Order(1)
//public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//
//	@Override
//	protected Class<?>[] getRootConfigClasses() {
//		return new Class[] {RootConfig.class, ServiceConfig.class, PersistenceConfig.class};
//	}
//
//	@Override
//	protected Class<?>[] getServletConfigClasses() {
//		return new Class[] { ServletConfig.class };
//	}
//
//	@Override
//	protected String[] getServletMappings() {
//		return new String[]{"/"};
//	}
//
//	@Override
//	protected Filter[] getServletFilters() {
//		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
//		encodingFilter.setEncoding("UTF-8");
//		encodingFilter.setForceEncoding(true);
//		return new Filter[] { encodingFilter };
//	}
//}
