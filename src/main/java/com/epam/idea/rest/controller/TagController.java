package com.epam.idea.rest.controller;

import com.epam.idea.core.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TagController {

	@Autowired
	private TagService tagService;
}
