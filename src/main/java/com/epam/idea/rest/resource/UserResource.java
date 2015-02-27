package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;
import java.util.List;

public class UserResource extends ResourceSupport {

	private String email;
	private String password;
	private ZonedDateTime creationTime;
	private List<Idea> ideas;
	private List<Comment> comments;
	private List<Role> roles;

	public UserResource() {
		//empty
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

	public List<Idea> getIdeas() {
		return ideas;
	}

	public void setIdeas(List<Idea> ideas) {
		this.ideas = ideas;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public User toUser(final UserResource resource) {
		return User.getBuilder()
				.withEmail(resource.getEmail())
				.withPassword(resource.getPassword())
				.withComments(resource.getComments())
				.withIdeas(resource.getIdeas())
				.withRoles(resource.getRoles())
				.build();
	}
}
