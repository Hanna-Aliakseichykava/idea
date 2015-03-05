package com.epam.idea.rest.resource;

import com.epam.idea.core.model.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserResource extends ResourceSupport {

	@Size(min = User.MIN_LENGTH_USERNAME, max = User.MAX_LENGTH_USERNAME)
	private String username;

	@Email
	@Size(min = User.MIN_LENGTH_EMAIL, max = User.MAX_LENGTH_EMAIL)
	private String email;

	@Size(min = User.MIN_LENGTH_PASSWORD, max = User.MAX_LENGTH_PASSWORD)
	private String password;
	private ZonedDateTime creationTime;
	private List<IdeaResource> ideas = new ArrayList<>();

	public UserResource() {
		//empty
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public List<IdeaResource> getIdeas() {
		return ideas;
	}

	public void setIdeas(final List<IdeaResource> ideas) {
		this.ideas = ideas;
	}

	public User toUser() {
		User.Builder user = User.getBuilder();
		user.withUsername(username);
		user.withEmail(email);
		user.withPassword(password);
		user.withIdeas(ideas.parallelStream()
				.map(IdeaResource::toIdea)
				.collect(Collectors.toList()));
		return user.build();
	}
}
