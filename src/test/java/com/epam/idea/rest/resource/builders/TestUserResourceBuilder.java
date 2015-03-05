package com.epam.idea.rest.resource.builders;

import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.UserResource;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestUserResourceBuilder {
	private String username;
	private String email;
	private String password;
	private ZonedDateTime creationTime;
	private List<IdeaResource> ideas = new ArrayList<>();

	private TestUserResourceBuilder() {
	}

	public static TestUserResourceBuilder aUserResource() {
		return new TestUserResourceBuilder();
	}

	public TestUserResourceBuilder withUsername(final String username) {
		this.username = username;
		return this;
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

	public TestUserResourceBuilder withIdeas(final List<IdeaResource> ideas) {
		this.ideas = ideas;
		return this;
	}

	public TestUserResourceBuilder but() {
		return aUserResource()
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.withCreationTime(creationTime)
				.withIdeas(ideas);
	}

	public UserResource build() {
		UserResource userResource = new UserResource();
		userResource.setUsername(username);
		userResource.setEmail(email);
		userResource.setPassword(password);
		userResource.setCreationTime(creationTime);
		userResource.setIdeas(ideas);
		return userResource;
	}
}
