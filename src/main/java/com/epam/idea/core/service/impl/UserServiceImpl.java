package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.repository.IdeaRepository;
import com.epam.idea.core.repository.UserRepository;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.IdeaExistsException;
import com.epam.idea.core.service.exception.UserDoesNotExistException;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private IdeaRepository ideaRepository;

	@Override
	public void delete(final User deleted) {
		userRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		List<User> userList = userRepository.findAll();
		userList.forEach(user -> {
			//Hibernate.initialize(user.getIdeas());
			Hibernate.initialize(user.getComments());
			Hibernate.initialize(user.getRoles());
		});
		return userList;
	}

	@Override
	@Transactional(readOnly = true)
	public User findOne(final Long id) {
		return userRepository.findOne(id).orElseThrow(UserDoesNotExistException::new);
	}

	@Override
	public User save(final User persisted) {
		return userRepository.save(persisted);
	}

	@Override
	public Idea createIdea(final Long userId, final Idea idea) {
		Optional<Idea> optionalIdea = ideaRepository.findByTitle(idea.getTitle());

		if (!optionalIdea.isPresent()) {
			throw new IdeaExistsException("The idea with the given title already exists");
		}

		User author = userRepository.findOne(userId).orElseThrow(UserDoesNotExistException::new);
		Idea createdIdea = Idea.getBuilderFrom(idea)
				.withAuthor(author)
				.build();
		ideaRepository.save(createdIdea);
		return createdIdea;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Idea> getIdeasOfUser(final Long userId) {
		return userRepository.findOne(userId).map(user -> {
			Hibernate.initialize(user.getIdeas());
			user.getIdeas().forEach(idea -> {
				Hibernate.initialize(idea.getComments());
				Hibernate.initialize(idea.getRelatedTags());
			});
			return user.getIdeas();
		}).orElseThrow(UserDoesNotExistException::new);
	}
}
