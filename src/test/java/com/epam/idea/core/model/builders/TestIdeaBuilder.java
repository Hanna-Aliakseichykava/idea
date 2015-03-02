package com.epam.idea.core.model.builders;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestIdeaBuilder {

	public static final String DEFAULT_TITLE = "Bar";
	public static final String DEFAULT_DESCRIPTION = "Lorem ipsum";
	public static final int DEFAULT_RATING = 0;
	public static final long DEFAULT_ID = 1L;
	public static final ZonedDateTime DEFAULT_CREATION_TIME = ZonedDateTime.of(2014, 2, 12, 10, 0, 0, 0, ZoneOffset.UTC);
	public static final ZonedDateTime DEFAULT_MODIFICATION_TIME = ZonedDateTime.of(2014, 10, 5, 0, 0, 0, 0, ZoneOffset.UTC);

	private Idea.Builder ideaBuilder;
	private int rating;
	private String title;
	private String description;
	private User author;
	private List<Tag> tags = new ArrayList<>(1);
	private List<Comment> comments = new ArrayList<>(1);

	private TestIdeaBuilder() {
		this.ideaBuilder = Idea.getBuilder();
	}

	public static TestIdeaBuilder anIdea() {
		return new TestIdeaBuilder()
				.withId(DEFAULT_ID)
				.withTitle(DEFAULT_TITLE)
				.withDescription(DEFAULT_DESCRIPTION)
				.withRating(DEFAULT_RATING)
				.withCreationTime(DEFAULT_CREATION_TIME)
				.withModificationTime(DEFAULT_MODIFICATION_TIME);
	}

	public TestIdeaBuilder withId(final long id) {
		ReflectionTestUtils.setField(ideaBuilder, "id", id);
		return this;
	}

	public TestIdeaBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	public TestIdeaBuilder withDescription(final String description) {
		this.description = description;
		return this;
	}

	public TestIdeaBuilder withCreationTime(final ZonedDateTime creationTime) {
		ReflectionTestUtils.setField(ideaBuilder, "creationTime", creationTime);
		return this;
	}

	public TestIdeaBuilder withModificationTime(final ZonedDateTime modificationTime) {
		ReflectionTestUtils.setField(ideaBuilder, "modificationTime", modificationTime);
		return this;
	}

	public TestIdeaBuilder withRating(final int rating) {
		this.rating = rating;
		return this;
	}

	public TestIdeaBuilder withAuthor(final User author) {
		this.author = author;
		return this;
	}

	public TestIdeaBuilder withTags(final List<Tag> tags) {
		this.tags = tags;
		return this;
	}

	public TestIdeaBuilder addTag(final Tag tag) {
		this.tags.add(tag);
		return this;
	}

	public TestIdeaBuilder withComments(final List<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public TestIdeaBuilder addComment(final Comment comment) {
		this.comments.add(comment);
		return this;
	}

	public TestIdeaBuilder but() {
		return anIdea()
				.withTitle(title)
				.withDescription(description)
				.withRating(rating)
				.withAuthor(author)
				.withTags(tags)
				.withComments(comments);
	}

	public Idea build() {
		return ideaBuilder
				.withTitle(title)
				.withDescription(description)
				.withAuthor(author)
				.withRating(rating)
				.withComments(comments)
				.withTags(tags)
				.build();
	}
}
