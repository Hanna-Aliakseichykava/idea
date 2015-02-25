package com.epam.idea.rest.controller;

import com.epam.idea.core.service.UserService;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<UserResource> login(@RequestBody UserResource userResource) {
		return new ResponseEntity<UserResource>(HttpStatus.OK);
		
	}
}
