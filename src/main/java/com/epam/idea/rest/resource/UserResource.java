package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import org.hibernate.validator.constraints.Email;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserResource extends ResourceSupport {

	@Email
	@Size(min = User.MIN_LENGTH_EMAIL, max = User.MAX_LENGTH_EMAIL)
	private String email;

	@Size(min = User.MIN_LENGTH_PASSWORD, max = User.MAX_LENGTH_PASSWORD)
	private String password;
	private ZonedDateTime creationTime;
	private List<Idea> ideas = new ArrayList<>();
	private List<Comment> comments = new ArrayList<>();
	private List<Role> roles = new ArrayList<>();

	public UserResource() {
		//empty
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public User toUser() {
		return User.getBuilder()
				.withEmail(email)
				.withPassword(password)
				.withComments(comments)
				.withIdeas(ideas)
				.withRoles(roles)
				.build();
	}
}
