package com.epam.idea.rest.resource;

import com.epam.idea.core.model.Tag;
import org.springframework.hateoas.ResourceSupport;

public class TagResource extends ResourceSupport {

	private String name;

	private int ideasCount;

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

	public int getIdeasCount() {
		return ideasCount;
	}

	public void setIdeasCount(int ideasCount) {
		this.ideasCount = ideasCount;
	}

	public Tag toTag() {
		final Tag tag = new Tag();
		tag.setName(name);
		return tag;
	}

}
