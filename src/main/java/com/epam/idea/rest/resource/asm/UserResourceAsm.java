package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.User;
import com.epam.idea.rest.endpoint.UserRestEndpoint;
import com.epam.idea.rest.resource.UserResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Objects.requireNonNull;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

	public static final String IDEAS_REL = "ideas";
	public static final String COMMENTS_REL = "comments";

	public UserResourceAsm() {
		super(UserRestEndpoint.class, UserResource.class);
	}

	@Override
	public UserResource toResource(final User original) {
		requireNonNull(original, "User cannot be null");
		final UserResource userResource = new UserResource();
		userResource.setUserId(original.getId());
		userResource.setUsername(original.getUsername());
		userResource.setEmail(original.getEmail());
		userResource.setCreationTime(original.getCreationTime());
		userResource.add(linkTo(methodOn(UserRestEndpoint.class).getUser(original.getId())).withSelfRel());
		userResource.add(linkTo(methodOn(UserRestEndpoint.class).getUserIdeas(original.getId())).withRel(IDEAS_REL));
		userResource.add(linkTo(methodOn(UserRestEndpoint.class).getUserComments(original.getId())).withRel(COMMENTS_REL));
		return userResource;
	}
}
