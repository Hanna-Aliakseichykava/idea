package com.epam.idea.rest.resource.builders;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.rest.resource.UserResource;

import java.time.ZonedDateTime;
import java.util.List;

public class TestUserResourceBuilder {
	private String email;
	private String password;
	private ZonedDateTime creationTime;
	private List<Idea> ideas;
	private List<Comment> comments;
	private List<Role> roles;

	private TestUserResourceBuilder() {
	}

	public static TestUserResourceBuilder aUserResource() {
		return new TestUserResourceBuilder();
	}

	public TestUserResourceBuilder withEmail(final String email) {
		this.email = email;
		return this;
	}

	public TestUserResourceBuilder withPassword(final String password) {
		this.password = password;
		return this;
	}

	public TestUserResourceBuilder withCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public TestUserResourceBuilder withIdeas(final List<Idea> ideas) {
		this.ideas = ideas;
		return this;
	}

	public TestUserResourceBuilder withComments(final List<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public TestUserResourceBuilder withRoles(final List<Role> roles) {
		this.roles = roles;
		return this;
	}

	public TestUserResourceBuilder but() {
		return aUserResource()
				.withEmail(email)
				.withPassword(password)
				.withCreationTime(creationTime)
				.withIdeas(ideas)
				.withComments(comments)
				.withRoles(roles);
	}

	public UserResource build() {
		UserResource userResource = new UserResource();
		userResource.setEmail(email);
		userResource.setPassword(password);
		userResource.setCreationTime(creationTime);
		userResource.setIdeas(ideas);
		userResource.setComments(comments);
		userResource.setRoles(roles);
		return userResource;
	}
}
