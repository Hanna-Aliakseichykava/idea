package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Tag;
import org.springframework.hateoas.ResourceSupport;

public class TagResource extends ResourceSupport {

	private String name;

	public TagResource() {
		//empty
	}

	public TagResource(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public Tag toTag() {
		final Tag.Builder tag = Tag.getBuilder();
		tag.withName(name);
		return tag.build();
	}
}
