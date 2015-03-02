package com.epam.idea.core.model;

import com.epam.idea.core.model.builders.TestUserBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

	@Test
	public void shouldAddCreationTime() {
		User user = User.getBuilder().build();
		user.prePersist();

		assertThat(user.getEmail()).isNull();
		assertThat(user.getPassword()).isNull();
		assertThat(user.getCreationTime()).isNotNull();
		assertThat(user.getIdeas()).hasSize(0);
		assertThat(user.getComments()).hasSize(0);
		assertThat(user.getRoles()).hasSize(0);
	}

	@Test
	public void shouldUpdateUser() throws Exception {
		// Given:
		User target = new TestUserBuilder()
				.withEmail("mail")
				.withPassword("pass")
				.build();
		User source = new TestUserBuilder()
				.withEmail("Foo")
				.withPassword("Bar")
				.build();

		// When:
		target.updateWith(source);

		// Then:
		assertThat(target.getEmail()).isEqualTo("Foo");
		assertThat(target.getPassword()).isEqualTo("Bar");
	}
}
