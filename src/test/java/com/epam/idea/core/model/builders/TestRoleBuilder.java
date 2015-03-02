package com.epam.idea.core.model.builders;

import com.epam.idea.core.model.Authority;
import com.epam.idea.core.model.Role;
import com.epam.idea.core.model.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;


public class TestRoleBuilder {

	public static final long DEFAULT_ID = 1L;

	private Role.Builder roleBuilder;
	private Authority name;
	private List<User> users = new ArrayList<>(1);

	private TestRoleBuilder() {
		this.roleBuilder = Role.getBuilder();
	}

	public static TestRoleBuilder aRole() {
		return new TestRoleBuilder()
				.withId(DEFAULT_ID)
				.withName(Authority.USER);
	}

	public static TestRoleBuilder anUserRole() {
		return aRole();
	}

	public static TestRoleBuilder anAdminRole() {
		return new TestRoleBuilder()
				.withName(Authority.ADMIN);
	}

	public TestRoleBuilder withId(final long id) {
		ReflectionTestUtils.setField(roleBuilder, "id", id);
		return this;
	}

	public TestRoleBuilder withName(final Authority name) {
		this.name = name;
		return this;
	}

	public TestRoleBuilder withUsers(final List<User> users) {
		this.users = users;
		return this;
	}

	public TestRoleBuilder addUser(final User user) {
		this.users.add(user);
		return this;
	}

	public TestRoleBuilder but() {
		return aRole()
				.withName(name)
				.withUsers(users);
	}

	public Role build() {
		return roleBuilder
				.withName(name)
				.withUsers(users)
				.build();
	}
}
