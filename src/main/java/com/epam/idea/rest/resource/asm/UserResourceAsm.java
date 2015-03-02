package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.User;
import com.epam.idea.rest.controller.UserController;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.Collections;

import static org.hibernate.Hibernate.isInitialized;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

	public UserResourceAsm() {
		super(UserController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(final User original) {
		UserResource userResource = new UserResource();
        userResource.setEmail(original.getEmail());
		userResource.setCreationTime(original.getCreationTime());
		if (isInitialized(original.getComments())) {
			userResource.setComments(original.getComments());
		} else {
			userResource.setComments(Collections.emptyList());
		}
		if (isInitialized(original.getIdeas())) {
			userResource.setIdeas(original.getIdeas());
		} else {
			userResource.setIdeas(Collections.emptyList());
		}
		if (isInitialized(original.getRoles())) {
			userResource.setRoles(original.getRoles());
		} else {
			userResource.setRoles(Collections.emptyList());
		}
		userResource.add(linkTo(methodOn(UserController.class).show(original.getId())).withSelfRel());
		return userResource;
	}
}
