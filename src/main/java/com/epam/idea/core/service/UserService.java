package com.epam.idea.core.service;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;

import java.util.List;

public interface UserService extends BaseService<User, Long> {

	Idea createIdea(Long userId, Idea idea);

	List<Idea> getIdeasOfUser(Long userId);
}
