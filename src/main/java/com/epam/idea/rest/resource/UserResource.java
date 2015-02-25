package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

public class UserResource extends ResourceSupport {

	private String email;
	private String password;
	private ZonedDateTime creationTime;
	private Set<Idea> ideas = new HashSet<>();
	private Set<Comment> comments = new HashSet<>();
	private Set<Role> roles = new HashSet<>();

	public UserResource() {
		//empty
	}

	public UserResource(final User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.creationTime = user.getCreationTime();
		this.ideas = user.getIdeas();
		this.comments = user.getComments();
		this.roles = user.getRoles();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public Set<Idea> getIdeas() {
		return ideas;
	}

	public void setIdeas(Set<Idea> ideas) {
		this.ideas = ideas;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User toUser(UserResource resource) {
		return User.getBuilder()
				.withEmail(resource.getEmail())
				.withPassword(resource.getPassword())
				.withComments(resource.getComments())
				.withIdeas(resource.getIdeas())
				.withRoles(resource.getRoles())
				.build();
	}
}
