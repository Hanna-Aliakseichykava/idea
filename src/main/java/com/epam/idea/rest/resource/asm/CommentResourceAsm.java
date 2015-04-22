package com.epam.idea.rest.resource.asm;

import java.util.Objects;

import com.epam.idea.core.model.Comment;
import com.epam.idea.rest.endpoint.CommentRestEndpoint;
import com.epam.idea.rest.resource.CommentResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CommentResourceAsm extends ResourceAssemblerSupport<Comment, CommentResource> {

	@Autowired
	private UserResourceAsm userResourceAsm;

	public CommentResourceAsm() {
		super(CommentRestEndpoint.class, CommentResource.class);
	}

	@Override
	public CommentResource toResource(final Comment original) {
		Objects.requireNonNull(original, "Comment must not be null");
		final CommentResource commentResource = new CommentResource();
		commentResource.setCommentId(original.getId());
		commentResource.setBody(original.getBody());
		commentResource.setCreationTime(original.getCreationTime());
		commentResource.setModificationTime(original.getModificationTime());
		commentResource.setRating(original.getRating());
		commentResource.setAuthor(this.userResourceAsm.toResource(original.getAuthor()));
		//todo add links
		return commentResource;
	}

}
