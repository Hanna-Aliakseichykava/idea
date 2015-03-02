package com.epam.idea.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "NAME", nullable = false)
	private Authority name;

	@ManyToMany
	@JoinTable(name = "USER_ROLE",
			joinColumns = @JoinColumn(name = "ROLE_ID"),
			inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private List<User> usersWithRole;

	public Role() {
		//empty
	}

	private Role(final Builder builder) {
		this.name =          builder.name;
		this.usersWithRole = builder.usersWithRole;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static Builder getBuilderFrom(final Role role) {
		return new Builder()
				.withId(role.id)
				.withName(role.name)
				.withUsers(role.usersWithRole);
	}

	public long getId() {
		return id;
	}

	public Authority getName() {
		return name;
	}

	public List<User> getUsersWithRole() {
		return usersWithRole;
	}

	public static class Builder {
		private long id;
		private Authority name;
		private List<User> usersWithRole = new ArrayList<>();

		private Builder() {
			//empty
		}

		private Builder withId(final long id) {
			this.id = id;
			return this;
		}

		public Builder withName(final Authority name) {
			this.name = name;
			return this;
		}

		public Builder withUsers(final List<User> usersWithRole) {
			this.usersWithRole = usersWithRole;
			return this;
		}

		public Builder addUser(final User user) {
			this.usersWithRole.add(user);
			return this;
		}

		public Role build() {
			return new Role(this);
		}
	}

	@Override
	public String toString() {
		return "Role{" +
				"id=" + id +
				", name=" + name +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Role role = (Role) o;

		if (name != role.name) return false;
		if (usersWithRole != null ? !usersWithRole.equals(role.usersWithRole) : role.usersWithRole != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + (usersWithRole != null ? usersWithRole.hashCode() : 0);
		return result;
	}
}
