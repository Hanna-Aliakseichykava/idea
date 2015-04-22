package com.epam.idea.core.repository;

import com.epam.idea.core.model.Tag;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends BaseRepository<Tag, Long> {

	/**
	 * Return the number of ideas marked by the tag with the given name.
	 *
	 * @param name The name of the tag.
	 * @return The number of ideas.
	 */
	@Query("select count(i) from Idea i inner join i.relatedTags tag where tag.name = ?1")
	int getIdeasCountForTag(String name);

}
