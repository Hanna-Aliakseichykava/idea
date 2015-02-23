package com.epam.idea.service.impl;

import com.epam.idea.domain.Role;
import com.epam.idea.repository.RoleRepository;
import com.epam.idea.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
	public Optional<Role> findOne(Long id) {
		return roleRepository.findOne(id);
	}

	@Override
	public Role save(Role persisted) {
		return roleRepository.save(persisted);
	}
}
