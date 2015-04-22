package com.epam.idea.rest.resource.asm;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.epam.idea.core.model.Idea;
import com.epam.idea.rest.endpoint.IdeaRestEndpoint;
import com.epam.idea.rest.endpoint.UserRestEndpoint;
import com.epam.idea.rest.resource.IdeaResource;
import com.epam.idea.rest.resource.TagResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class IdeaResourceAsm extends ResourceAssemblerSupport<Idea, IdeaResource> {

	public static final String REL_AUTHOR = "author";

	@Autowired
	private TagResourceAsm tagResourceAsm;

	@Autowired
	private UserResourceAsm userResourceAsm;

	public IdeaResourceAsm() {
		super(IdeaRestEndpoint.class, IdeaResource.class);
	}

	@Override
	public IdeaResource toResource(final Idea original) {
		Objects.requireNonNull(original, "Idea must not be null");
		final IdeaResource ideaResource = new IdeaResource();
		ideaResource.setIdeaId(original.getId());
		ideaResource.setTitle(original.getTitle());
		ideaResource.setDescription(original.getDescription());
		ideaResource.setCreationTime(original.getCreationTime());
		ideaResource.setModificationTime(original.getModificationTime());
		ideaResource.setRating(original.getRating());
		ideaResource.setAuthor(this.userResourceAsm.toResource(original.getAuthor()));
		List<TagResource> tagResources = original.getRelatedTags().parallelStream()
				.map(this.tagResourceAsm::toResource)
				.collect(Collectors.toList());
		ideaResource.setTags(tagResources);
		ideaResource.add(linkTo(methodOn(IdeaRestEndpoint.class).show(original.getId())).withSelfRel());
		ideaResource.add(linkTo(methodOn(UserRestEndpoint.class)
				.getUser(original.getAuthor().getId())).withRel(REL_AUTHOR));
		//todo add link to comments
		return ideaResource;
	}

}
