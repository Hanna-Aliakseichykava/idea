package com.epam.idea.rest.controller;

import com.epam.idea.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
}
