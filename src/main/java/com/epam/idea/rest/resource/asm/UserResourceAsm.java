package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.User;
import com.epam.idea.rest.controller.UserController;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Objects.requireNonNull;
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
		userResource.add(linkTo(methodOn(UserController.class).show(original.getId())).withSelfRel());
		return userResource;
	}
}
