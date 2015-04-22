package com.epam.idea.core.model;

import com.epam.idea.builder.model.TestIdeaBuilder;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IdeaTest {

	@Test
	public void shouldUpdateUser() throws Exception {
		// Given:
		Idea target = new TestIdeaBuilder()
				.withTitle("title")
				.withDescription("description")
				.build();
		Idea source = new TestIdeaBuilder()
				.withTitle("New title")
				.withDescription("New description")
				.build();

		// When:
		target.updateWith(source);

		// Then:
		assertThat(target.getTitle()).isEqualTo(source.getTitle());
		assertThat(target.getDescription()).isEqualTo(source.getDescription());
	}

}
