package com.epam.idea.service.impl;

import com.epam.idea.domain.User;
import com.epam.idea.repository.UserRepository;
import com.epam.idea.service.UserService;
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

	@Override
	public void delete(User deleted) {
		userRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findOne(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User save(User persisted) {
		return userRepository.save(persisted);
	}
}
