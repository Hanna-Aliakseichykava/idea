package com.epam.idea.rest.resource;

import org.springframework.hateoas.ResourceSupport;

import java.util.HashSet;
import java.util.Set;

public class IdeaSetResource extends ResourceSupport {

	private Set<IdeaResource> ideas = new HashSet<>();

	public Set<IdeaResource> getIdeas() {
		return ideas;
	}

	public void setIdeas(Set<IdeaResource> ideas) {
		this.ideas = ideas;
	}
}
