package com.epam.idea.core.service;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;

import java.util.Set;

public interface UserService extends BaseService<User, Long> {

	Idea createIdea(Long userId, Idea idea);

	Set<Idea> findAllIdeasByUserId(Long userId);
}
