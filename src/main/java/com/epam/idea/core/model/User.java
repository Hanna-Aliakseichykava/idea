package com.epam.idea.core.model;

import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@Column(name = "CREATION_TIME", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime creationTime;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Idea> ideas;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE",
			joinColumns = @JoinColumn(name = "USER_ID"),
			inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	private List<Role> roles;

	public User() {
		//empty
	}

	private User(final Builder builder) {
		this.id =           builder.id;
		this.email =        builder.email;
		this.password =     builder.password;
		this.creationTime = builder.creationTime;
		this.ideas =        builder.ideas;
		this.comments =     builder.comments;
		this.roles =        builder.roles;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static Builder getBuilderFrom(final User user) {
		return new Builder()
				.withId(user.id)
				.withEmail(user.email)
				.withPassword(user.password)
				.withCreationTime(user.creationTime)
				.withIdeas(user.ideas)
				.withComments(user.comments)
				.withRoles(user.roles);
	}

	public long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public List<Idea> getIdeas() {
		return ideas;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public List<Role> getRoles() {
		return roles;
	}

	@PrePersist
	public void prePersist() {
		this.creationTime = ZonedDateTime.now();
	}

	public static class Builder {
		private long id;
		private String email;
		private String password;
		private ZonedDateTime creationTime;
		private List<Idea> ideas = new ArrayList<>();
		private List<Comment> comments = new ArrayList<>();
		private List<Role> roles = new ArrayList<>();

		private Builder withId(final long id) {
			this.id = id;
			return this;
		}

		public Builder withEmail(final String email) {
			this.email = email;
			return this;
		}

		public Builder withPassword(final String password) {
			this.password = password;
			return this;
		}

		private Builder withCreationTime(final ZonedDateTime creationTime) {
			this.creationTime = creationTime;
			return this;
		}

		public Builder withIdeas(final List<Idea> ideas) {
			this.ideas = ideas;
			return this;
		}

		public Builder addIdea(final Idea idea) {
			this.ideas.add(idea);
			return this;
		}

		public Builder withComments(final List<Comment> comments) {
			this.comments = comments;
			return this;
		}

		public Builder addComment(final Comment comment) {
			this.comments.add(comment);
			return this;
		}

		public Builder withRoles(final List<Role> roles) {
			this.roles = roles;
			return this;
		}

		public Builder addRole(final Role role) {
			this.roles.add(role);
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", creationTime=" + creationTime +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (comments != null ? !comments.equals(user.comments) : user.comments != null) return false;
		if (!creationTime.equals(user.creationTime)) return false;
		if (!email.equals(user.email)) return false;
		if (ideas != null ? !ideas.equals(user.ideas) : user.ideas != null) return false;
		if (!password.equals(user.password)) return false;
		if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = (int) (id ^ (id >>> 32));
		result = 31 * result + email.hashCode();
		result = 31 * result + password.hashCode();
		result = 31 * result + creationTime.hashCode();
		result = 31 * result + (ideas != null ? ideas.hashCode() : 0);
		result = 31 * result + (comments != null ? comments.hashCode() : 0);
		result = 31 * result + (roles != null ? roles.hashCode() : 0);
		return result;
	}
}
