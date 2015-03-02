package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.Idea;
import com.epam.idea.rest.controller.IdeaController;
import com.epam.idea.rest.resource.IdeaResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static java.util.Collections.emptyList;
import static org.hibernate.Hibernate.isInitialized;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class IdeaResourceAsm extends ResourceAssemblerSupport<Idea, IdeaResource> {

	public IdeaResourceAsm() {
		super(IdeaController.class, IdeaResource.class);
	}

	@Override
	public IdeaResource toResource(final Idea original) {
		IdeaResource ideaResource = new IdeaResource();
		ideaResource.setTitle(original.getTitle());
		ideaResource.setDescription(original.getDescription());
		ideaResource.setCreationTime(original.getCreationTime());
		ideaResource.setModificationTime(original.getModificationTime());
		ideaResource.setRating(original.getRating());
		if (isInitialized(original.getComments())) {
			ideaResource.setComments(original.getComments());
		} else {
			ideaResource.setComments(emptyList());
		}
		if (isInitialized(original.getRelatedTags())) {
			ideaResource.setRelatedTags(original.getRelatedTags());
		} else {
			ideaResource.setRelatedTags(emptyList());
		}
		if (isInitialized(original.getAuthor())) {
			ideaResource.setAuthor(original.getAuthor());
		}
		ideaResource.add(linkTo(methodOn(IdeaController.class).show(original.getId())).withSelfRel());
		return ideaResource;
	}
}
