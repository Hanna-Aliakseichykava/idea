package com.epam.idea.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<T, ID extends Serializable> {
	void delete(T deleted);

	List<T> findAll();

	Optional<T> findOne(ID id);

	T save(T persisted);
}
