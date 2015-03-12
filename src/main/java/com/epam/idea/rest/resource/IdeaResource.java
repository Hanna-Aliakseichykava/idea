package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.User;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class IdeaResource extends ResourceSupport {

	@Size(min = Idea.MIN_LENGTH_TITLE, max = Idea.MAX_LENGTH_TITLE)
	private String title;

	@Size(max = Idea.MAX_LENGTH_DESCRIPTION)
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

	public IdeaResource(String title, String description, ZonedDateTime creationTime, ZonedDateTime modificationTime, int rating, User author, List<Tag> relatedTags, List<Comment> comments) {
		this.title = title;
		this.description = description;
		this.creationTime = creationTime;
		this.modificationTime = modificationTime;
		this.rating = rating;
		this.author = author;
		this.relatedTags = relatedTags;
		this.comments = comments;
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
