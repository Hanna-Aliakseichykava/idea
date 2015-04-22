package com.epam.idea.rest.endpoint;

import java.util.List;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.TagService;
import com.epam.idea.rest.config.annotation.RestEndpoint;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import com.epam.idea.rest.resource.asm.TagResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestEndpoint
@RequestMapping("/api/v1/tags")
public class TagRestEndpoint {

	@Autowired
	private TagService tagService;

	@Autowired
	private IdeaService ideaService;

	@Autowired
	private TagResourceAsm tagResourceAsm;

	@Autowired
	private IdeaResourceAsm ideaResourceAsm;

	@RequestMapping(value = "/{tagId}", method = RequestMethod.GET)
	public HttpEntity<TagResource> getTag(@PathVariable final long tagId) {
		final Tag foundTag = this.tagService.findOne(tagId);
		final TagResource tagResource = this.tagResourceAsm.toResource(foundTag);
		return new ResponseEntity<>(tagResource, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<TagResource>> getAllTags() {
		final List<Tag> foundTags = this.tagService.findAll();
		final List<TagResource> tagResources = this.tagResourceAsm.toResources(foundTags);
		return new ResponseEntity<>(tagResources, HttpStatus.OK);
	}

	@RequestMapping(value = "/{tagId}/ideas", method = RequestMethod.GET)
	public HttpEntity<List<IdeaResource>> getAllFoundIdeasForTag(@PathVariable final long tagId) {
		final List<Idea> foundIdeas = this.ideaService.findIdeasByTagId(tagId);
		final List<IdeaResource> ideaResources = this.ideaResourceAsm.toResources(foundIdeas);
		return new ResponseEntity<>(ideaResources, HttpStatus.OK);
	}

}
