package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/api/v1/ideas")
public class IdeaController {

	@Autowired
	private IdeaService ideaService;

	@RequestMapping(value = "/{ideaId}", method = RequestMethod.GET)
	public HttpEntity<IdeaResource> show(@PathVariable final long ideaId) {
		Idea idea = ideaService.findOne(ideaId);
		IdeaResource ideaResource = new IdeaResourceAsm().toResource(idea);
		return new ResponseEntity<>(ideaResource, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<IdeaResource>> showAll() {
		List<Idea> ideas = ideaService.findAll();
		List<IdeaResource> ideaResources = new IdeaResourceAsm().toResources(ideas);
		return new ResponseEntity<>(ideaResources, HttpStatus.OK);
	}

	@RequestMapping
}
