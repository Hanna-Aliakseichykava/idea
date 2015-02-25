package com.epam.idea.rest.controller;

import com.epam.idea.core.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class IdeaController {

	@Autowired
	private IdeaService ideaService;
}
