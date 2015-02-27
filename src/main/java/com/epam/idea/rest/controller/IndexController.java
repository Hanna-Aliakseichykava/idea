package com.epam.idea.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


//	@Autowired
//	AnnotationConfigWebApplicationContext context;

	@RequestMapping(value = "/")
	public String showIndex() {
		System.out.println("");
		return "view";
	}
}
