package com.epam.idea.rest.resource.asm;

import com.epam.idea.core.model.Idea;
import com.epam.idea.rest.controller.IdeaController;
import com.epam.idea.rest.resource.IdeaResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class IdeaResourceAsm extends ResourceAssemblerSupport<Idea, IdeaResource> {

	public IdeaResourceAsm() {
		super(IdeaController.class, IdeaResource.class);
	}

	@Override
	public IdeaResource toResource(Idea original) {
		return new IdeaResource(original);
	}
}
