package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Idea;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class IdeaResource extends ResourceSupport {

	@Size(min = Idea.MIN_LENGTH_TITLE, max = Idea.MAX_LENGTH_TITLE)
	private String title;

	@Size(max = Idea.MAX_LENGTH_DESCRIPTION)
	private String description;
	private ZonedDateTime creationTime;
	private ZonedDateTime modificationTime;
	private int rating;
	private UserResource author;
	private List<TagResource> tags = new ArrayList<>();

	public IdeaResource() {
		//empty
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

	public UserResource getAuthor() {
		return author;
	}

	public void setAuthor(UserResource author) {
		this.author = author;
	}

	public List<TagResource> getTags() {
		return tags;
	}

	public void setTags(List<TagResource> tags) {
		this.tags = tags;
	}
	
	public Idea toIdea() {
		final Idea.Builder idea = Idea.getBuilder();
		if (author != null) {
			idea.withAuthor(author.toUser());
		}
		idea.withDescription(description);
		idea.withRating(rating);
		idea.withTags(tags.parallelStream()
				.map(TagResource::toTag)
				.collect(Collectors.toList()));
		idea.withTitle(title);
		return idea.build();
	}
}
