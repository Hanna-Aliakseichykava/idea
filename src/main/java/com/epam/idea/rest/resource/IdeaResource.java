package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class IdeaResource extends ResourceSupport {

	private String title;
	private String description;
	private ZonedDateTime creationTime;
	private ZonedDateTime modificationTime;
	private int rating;
	private User author;
	private List<Tag> relatedTags = new ArrayList<>();
	private List<Comment> comments = new ArrayList<>();

	public IdeaResource() {
		//empty
	}

	public IdeaResource(final Idea idea) {
		this.title = idea.getTitle();
		this.description = idea.getDescription();
		this.creationTime = idea.getCreationTime();
		this.modificationTime = idea.getModificationTime();
		this.rating = idea.getRating();
		this.author = idea.getAuthor();
		this.relatedTags = idea.getRelatedTags();
		this.comments = idea.getComments();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(ZonedDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public ZonedDateTime getModificationTime() {
		return modificationTime;
	}

	public void setModificationTime(ZonedDateTime modificationTime) {
		this.modificationTime = modificationTime;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<Tag> getRelatedTags() {
		return relatedTags;
	}

	public void setRelatedTags(List<Tag> relatedTags) {
		this.relatedTags = relatedTags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public Idea toIdea() {
		return Idea.getBuilder()
				.withAuthor(author)
				.withComments(comments)
				.withDescription(description)
				.withRating(rating)
				.withTags(relatedTags)
				.withTitle(title)
				.build();
	}
}
