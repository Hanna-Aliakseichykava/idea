package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.service.IdeaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
		return ideaRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Idea> findOne(Long id) {
		return ideaRepository.findOne(id);
	}

	@Override
	public Idea save(Idea persisted) {
		return ideaRepository.save(persisted);
	}
}
