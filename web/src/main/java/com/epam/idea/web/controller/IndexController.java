package com.epam.idea.web.controller;

import com.epam.idea.domain.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {


//	@Autowired
//	AnnotationConfigWebApplicationContext context;

	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public User showIndex(@RequestBody User user) {
		return user;
	}
}
