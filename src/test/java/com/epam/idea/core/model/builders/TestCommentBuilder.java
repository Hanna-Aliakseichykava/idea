package com.epam.idea.core.model.builders;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class TestCommentBuilder {

	public static final String DEFAULT_BODY = "Neque porro quisquam est qui dolorem ipsum quia dolor sit amet";
	public static final int DEFAULT_RATING = 0;
	public static final long DEFAULT_ID = 1L;
	public static final ZonedDateTime DEFAULT_CREATION_TIME = ZonedDateTime.of(2014, 5, 5, 0, 0, 0, 0, ZoneOffset.UTC);
	public static final ZonedDateTime DEFAULT_MODIFICATION_TIME = ZonedDateTime.of(2014, 7, 8, 0, 0, 0, 0, ZoneOffset.UTC);

	private Comment.Builder commentBuilder;
	private String body;
	private int rating;
	private User author;
	private Idea subject;

	private TestCommentBuilder() {
		this.commentBuilder = Comment.getBuilder();
	}

	public static TestCommentBuilder aComment() {
		return new TestCommentBuilder()
				.withId(DEFAULT_ID)
				.withModificationTime(DEFAULT_MODIFICATION_TIME)
				.withCreationTime(DEFAULT_CREATION_TIME)
				.withBody(DEFAULT_BODY)
				.withRating(DEFAULT_RATING);
	}

	public TestCommentBuilder withId(final long id) {
		ReflectionTestUtils.setField(commentBuilder, "id", id);
		return this;
	}
	
	public TestCommentBuilder withBody(final String body) {
		this.body = body;
		return this;
	}

	public TestCommentBuilder withRating(final int rating) {
		this.rating = rating;
		return this;
	}

	public TestCommentBuilder withCreationTime(final ZonedDateTime creationTime) {
		ReflectionTestUtils.setField(commentBuilder, "creationTime", creationTime);
		return this;
	}

	public TestCommentBuilder withModificationTime(final ZonedDateTime modificationTime) {
		ReflectionTestUtils.setField(commentBuilder, "modificationTime", modificationTime);
		return this;
	}

	public TestCommentBuilder withAuthor(final User author) {
		this.author = author;
		return this;
	}

	public TestCommentBuilder withSubject(final Idea subject) {
		this.subject = subject;
		return this;
	}

	public TestCommentBuilder but() {
		return aComment()
				.withBody(body)
				.withRating(rating)
				.withAuthor(author)
				.withSubject(subject);
	}

	public Comment build() {
		return commentBuilder
				.withBody(body)
				.withRating(rating)
				.withAuthor(author)
				.withSubject(subject)
				.build();
	}
}