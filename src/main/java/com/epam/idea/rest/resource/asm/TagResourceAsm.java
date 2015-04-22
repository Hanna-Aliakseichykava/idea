package com.epam.idea.rest.resource.asm;

import java.util.Objects;

import com.epam.idea.core.model.Tag;
import com.epam.idea.core.service.TagService;
import com.epam.idea.rest.endpoint.TagRestEndpoint;
import com.epam.idea.rest.resource.TagResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class TagResourceAsm extends ResourceAssemblerSupport<Tag, TagResource> {

	public static final String IDEAS_REL = "ideas";

	@Autowired
	private TagService tagService;

	public TagResourceAsm() {
		super(TagRestEndpoint.class, TagResource.class);
	}

	@Override
	public TagResource toResource(final Tag original) {
		Objects.requireNonNull(original, "Tag must not be null");
		final TagResource tagResource = new TagResource();
		tagResource.setName(original.getName());
		final int ideasCount = this.tagService.getIdeasCountForTag(original.getName());
		tagResource.setIdeasCount(ideasCount);
		tagResource.add(linkTo(methodOn(TagRestEndpoint.class).getTag(original.getId())).withSelfRel());
		tagResource.add(linkTo(methodOn(TagRestEndpoint.class).getAllFoundIdeasForTag(original.getId())).withRel(IDEAS_REL));
		return tagResource;
	}

}
