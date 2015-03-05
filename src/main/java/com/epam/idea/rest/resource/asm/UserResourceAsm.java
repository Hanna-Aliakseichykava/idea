package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.User;
import com.epam.idea.rest.controller.UserController;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.hibernate.Hibernate.isInitialized;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

	public UserResourceAsm() {
		super(UserController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(final User original) {
		requireNonNull(original);
		final UserResource userResource = new UserResource();
		userResource.setUsername(original.getUsername());
		userResource.setEmail(original.getEmail());
		userResource.setCreationTime(original.getCreationTime());
		if (isInitialized(original.getIdeas())) {
			List<IdeaResource> ideaResources = original.getIdeas().parallelStream()
					.map(idea -> new IdeaResourceAsm().toResource(idea))
					.collect(Collectors.toList());
			userResource.setIdeas(ideaResources);
		} else {
			userResource.setIdeas(Collections.emptyList());
		}
		userResource.add(linkTo(methodOn(UserController.class).show(original.getId())).withSelfRel());
		return userResource;
	}
}
