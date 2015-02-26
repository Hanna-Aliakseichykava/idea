package com.epam.idea.core.repository;

import com.epam.idea.core.model.Idea;

import java.util.Optional;

public interface IdeaRepository extends BaseRepository<Idea, Long> {
	Optional<Idea> findByTitle(String title);
}
