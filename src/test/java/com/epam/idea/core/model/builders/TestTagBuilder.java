package com.epam.idea.core.model.builders;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

public class TestTagBuilder {

	public static final String DEFAULT_NAME = "Foo";
	public static final long DEFAULT_ID = 1L;

	private Tag.Builder tagBuilder;
	private String name;
	private List<Idea> ideas = new ArrayList<>(1);

	private TestTagBuilder() {
		this.tagBuilder = Tag.getBuilder();
	}

	public static TestTagBuilder aTag() {
		return new TestTagBuilder()
				.withId(DEFAULT_ID)
				.withName(DEFAULT_NAME);
	}

	public TestTagBuilder withId(final long id) {
		ReflectionTestUtils.setField(tagBuilder, "id", id);
		return this;
	}

	public TestTagBuilder withName(final String name) {
		this.name = name;
		return this;
	}

	public TestTagBuilder withIdeas(final List<Idea> ideas) {
		this.ideas = ideas;
		return this;
	}

	public TestTagBuilder addIdea(final Idea idea) {
		this.ideas.add(idea);
		return this;
	}

	public TestTagBuilder but() {
		return aTag()
				.withName(name)
				.withIdeas(ideas);
	}

	public Tag build() {
		return tagBuilder
				.withName(name)
				.withIdeas(ideas)
				.build();
	}
}
