package com.epam.idea.core.service;

import com.epam.idea.core.model.Tag;

public interface TagService extends BaseService<Tag, Long> {

	/**
	 * Return the number of ideas marked by the tag with the given name.
	 *
	 * @param name The name of the tag.
	 * @return The number of ideas.
	 */
	int getIdeasCountForTag(String name);

}
