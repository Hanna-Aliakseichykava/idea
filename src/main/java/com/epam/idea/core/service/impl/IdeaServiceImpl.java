package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

	@Autowired
	private IdeaRepository ideaRepository;

	@Override
	public void delete(Idea deleted) {
		ideaRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> findAll() {
		List<Idea> allIdeas = ideaRepository.findAll();
		allIdeas.forEach(idea -> {
			Hibernate.initialize(idea.getAuthor());
			Hibernate.initialize(idea.getRelatedTags());
		});
		return allIdeas;
	}

	@Override
	@Transactional(readOnly = true)
	public Idea findOne(Long ideaId) {
		return ideaRepository.findOne(ideaId).orElseThrow(() -> new IdeaNotFoundException(ideaId));
	}

	@Override
	public Idea save(Idea persisted) {
		return ideaRepository.save(persisted);
	}
}
