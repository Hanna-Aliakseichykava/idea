package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Comment;
import com.epam.idea.core.repository.CommentRepository;
import com.epam.idea.core.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void delete(Comment deleted) {
		commentRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Comment> findAll() {
		return commentRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Comment> findOne(Long id) {
		return commentRepository.findOne(id);
	}

	@Override
	public Comment save(Comment persisted) {
		return commentRepository.save(persisted);
	}
}
