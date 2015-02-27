package com.epam.idea.rest.controller;

import com.epam.idea.core.model.User;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.UserDoesNotExistException;
import com.epam.idea.rest.resource.UserResource;
import com.epam.idea.rest.resource.asm.UserResourceAsm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ApplicationContext webApplicationContext;

//	@RequestMapping(method = RequestMethod.POST)
//	public HttpEntity<UserResource> createUser(@RequestBody UserResource sentUser) {
//		return null;
//	}
	
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<UserResource>> showAll() {
		List<User> userList = userService.findAll();
		List<UserResource> userResources = new UserResourceAsm().toResources(userList);
		Link selfRel = linkTo(methodOn(UserController.class).showAll()).withSelfRel();
		//Resources<UserResource> wrapped = new Resources<>(userResources, selfRel);
		return new ResponseEntity<>(userResources, HttpStatus.OK);
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public HttpEntity<UserResource> show(@PathVariable long userId) {
		try {
			User user = userService.findOne(userId);
			UserResource res = new UserResourceAsm().toResource(user);
			return new ResponseEntity<>(res, HttpStatus.OK);
		} catch (UserDoesNotExistException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

//	@RequestMapping(value = "/{userId}/ideas", method = RequestMethod.POST)
//	public HttpEntity<IdeaResource> createIdea(@PathVariable final Long userId, @RequestBody final IdeaResource ideaRes) {
//		try {
//			Idea createdIdea = userService.createIdea(userId, ideaRes.toIdea());
//			IdeaResource createdIdeaRes = new IdeaResourceAsm().toResource(createdIdea);
//			HttpHeaders headers = new HttpHeaders();
//			headers.setLocation(URI.create(createdIdeaRes.getLink(REL_SELF).getHref()));
//			return new ResponseEntity<>(createdIdeaRes, headers, HttpStatus.CREATED);
//		} catch (UserDoesNotExistException e) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		} catch (IdeaExistsException e) {
//			return new ResponseEntity<>(HttpStatus.CONFLICT);
//		}
//	}

//	@RequestMapping(value = "/{userId}/ideas", method = RequestMethod.GET)
//	public HttpEntity<Resources<IdeaResource>> showIdeasOfUser(@PathVariable final long userId) {
//		try {
//			List<Idea> userIdeas = userService.getIdeasOfUser(userId);
//			IdeaResourceAsm ideaResourceAsm = new IdeaResourceAsm();
//			List<IdeaResource> ideaResources = ideaResourceAsm.toResources(userIdeas);
//			Resources<IdeaResource> wrapped = new Resources<>(ideaResources, linkTo(
//					methodOn(UserController.class).showIdeasOfUser(userId)).withSelfRel());
//			return new HttpEntity<>(wrapped);
//		} catch (UserDoesNotExistException e) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}

//	@RequestMapping(value = "/{userId}/comments", method = RequestMethod.GET)
//	public HttpEntity<Resources<CommentResource>> showCommentsOfUser(@PathVariable final long userId) {
//		return null;
//	}
}
