package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.rest.controller.IdeaController;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;
import static org.hibernate.Hibernate.isInitialized;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class IdeaResourceAsm extends ResourceAssemblerSupport<Idea, IdeaResource> {

	public IdeaResourceAsm() {
		super(IdeaController.class, IdeaResource.class);
	}

	@Override
	public IdeaResource toResource(final Idea original) {
		requireNonNull(original);
		final IdeaResource ideaResource = new IdeaResource();
		ideaResource.setTitle(original.getTitle());
		ideaResource.setDescription(original.getDescription());
		ideaResource.setCreationTime(original.getCreationTime());
		ideaResource.setModificationTime(original.getModificationTime());
		ideaResource.setRating(original.getRating());
		if (isInitialized(original.getRelatedTags())) {
			List<TagResource> tagResources = original.getRelatedTags().parallelStream()
					.map(tag -> new TagResourceAsm().toResource(tag))
					.collect(Collectors.toList());
			ideaResource.setTags(tagResources);
		} else {
			ideaResource.setTags(emptyList());
		}
		final User author = original.getAuthor();
		if (author != null && isInitialized(author)) {
			ideaResource.setAuthor(new UserResourceAsm().toResource(author));
		}
		ideaResource.add(linkTo(methodOn(IdeaController.class).show(original.getId())).withSelfRel());
		return ideaResource;
	}
}
