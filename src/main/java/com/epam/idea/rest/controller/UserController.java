package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.IdeaExistsException;
import com.epam.idea.core.service.exception.UserDoesNotExistException;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.UserResource;
import com.epam.idea.rest.resource.asm.IdeaResourceAsm;
import com.epam.idea.rest.resource.asm.UserResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.Link.REL_SELF;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<UserResource> createUser(@RequestBody UserResource sentUser) {
		return null;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public HttpEntity<UserResource> getUser(@PathVariable long userId) {
		try {
			User user = userService.findOne(userId);
			UserResource res = new UserResourceAsm().toResource(user);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/{userId}/ideas", method = RequestMethod.POST)
	public HttpEntity<IdeaResource> createIdea(@PathVariable final Long userId, @RequestBody final IdeaResource ideaRes) {
		try {
			Idea createdIdea = userService.createIdea(userId, ideaRes.toIdea());
			IdeaResource createdIdeaRes = new IdeaResourceAsm().toResource(createdIdea);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(createdIdeaRes.getLink(REL_SELF).getHref()));
			return new ResponseEntity<>(createdIdeaRes, headers, HttpStatus.CREATED);
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IdeaExistsException e) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}

	@RequestMapping(value = "/{userId}/ideas", method = RequestMethod.GET)
	public HttpEntity<List<User>> getAllIdeas(@PathVariable final long userId) {
		userService.findAllIdeasByUserId(userId);
		return null;
	}
}
