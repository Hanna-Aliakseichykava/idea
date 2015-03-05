package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Tag;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;
import java.util.stream.Collectors;

public class TagResource extends ResourceSupport {

	private String name;
	private List<IdeaResource> ideas;

	public TagResource() {
		//empty
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public List<IdeaResource> getIdeas() {
		return ideas;
	}

	public void setIdeas(final List<IdeaResource> ideas) {
		this.ideas = ideas;
	}

	public Tag toTag() {
		final Tag.Builder tag = Tag.getBuilder();
		tag.withName(name);
		tag.withIdeas(ideas.parallelStream()
				.map(IdeaResource::toIdea)
				.collect(Collectors.toList()));
		return tag.build();
	}
}
