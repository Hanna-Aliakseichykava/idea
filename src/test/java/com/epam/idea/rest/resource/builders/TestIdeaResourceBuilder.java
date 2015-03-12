package com.epam.idea.rest.resource.builders;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import com.epam.idea.rest.resource.IdeaResource;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestIdeaResourceBuilder {
	
	private String title;
	private String description;
	private ZonedDateTime creationTime;
	private ZonedDateTime modificationTime;
	private int rating;
	private User author;
	private List<Tag> relatedTags = new ArrayList<>();
	private List<Comment> comments = new ArrayList<>();

	private TestIdeaResourceBuilder() {
	}

	public static TestIdeaResourceBuilder anIdeaResource() {
		return new TestIdeaResourceBuilder();
	}

	public TestIdeaResourceBuilder withTitle(final String title) {
		this.title = title;
		return this;
	}

	public TestIdeaResourceBuilder withDescription(final String description) {
		this.description = description;
		return this;
	}

	public TestIdeaResourceBuilder withCreationTime(final ZonedDateTime creationTime) {
		this.creationTime = creationTime;
		return this;
	}

	public TestIdeaResourceBuilder withModificationTime(final ZonedDateTime modificationTime) {
		this.modificationTime = modificationTime;
		return this;
	}

	public TestIdeaResourceBuilder withRating(final int rating) {
		this.rating = rating;
		return this;
	}

	public TestIdeaResourceBuilder withAuthor(final User author) {
		this.author = author;
		return this;
	}

	public TestIdeaResourceBuilder withRelatedTags(final List<Tag> relatedTags) {
		this.relatedTags = relatedTags;
		return this;
	}

	public TestIdeaResourceBuilder withComments(final List<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public TestIdeaResourceBuilder but() {
		return anIdeaResource()
				.withTitle(title)
				.withDescription(description)
				.withCreationTime(creationTime)
				.withModificationTime(modificationTime)
				.withRating(rating)
				.withAuthor(author)
				.withRelatedTags(relatedTags)
				.withComments(comments);
	}

	public IdeaResource build() {
		IdeaResource ideaResource = new IdeaResource();
		ideaResource.setTitle(title);
		ideaResource.setDescription(description);
		ideaResource.setCreationTime(creationTime);
		ideaResource.setModificationTime(modificationTime);
		ideaResource.setRating(rating);
		ideaResource.setAuthor(author);
		ideaResource.setRelatedTags(relatedTags);
		ideaResource.setComments(comments);
		return ideaResource;
	}
}
