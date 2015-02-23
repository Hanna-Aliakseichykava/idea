package com.epam.idea.web.config.servlet;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.SpringDataWebConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring MVC config for the servlet context in the application.
 * The beans of this context are only visible inside the servlet context.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.idea.web.controller")
public class ServletConfig extends SpringDataWebConfiguration {
}
