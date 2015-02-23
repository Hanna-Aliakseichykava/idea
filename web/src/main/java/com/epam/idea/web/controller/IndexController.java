package com.epam.idea.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Controller
@RequestMapping("/")
public class IndexController {
	
//	@Autowired
//	private UserService userService;
	
	
	
	@Autowired
	AnnotationConfigWebApplicationContext context;

	@ResponseBody
	public String showIndex() {
		System.out.println("here");
//		Optional<User> one = userService.findOne(1l);
//		if (one.isPresent()) {
//			User user = one.get();
//			System.out.println(user);
//		}
		return "Hello world";
	}
}
