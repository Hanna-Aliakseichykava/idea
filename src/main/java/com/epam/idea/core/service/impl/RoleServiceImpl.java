package com.epam.idea.core.service.impl;

import com.epam.idea.core.model.Role;
import com.epam.idea.core.repository.RoleRepository;
import com.epam.idea.core.service.RoleService;
import com.epam.idea.core.service.exception.RoleDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public void delete(Role deleted) {
		roleRepository.delete(deleted);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Role findOne(Long id) {
		return roleRepository.findOne(id).orElseThrow(RoleDoesNotExistException::new);
	}

	@Override
	public Role save(Role persisted) {
		return roleRepository.save(persisted);
	}
}
