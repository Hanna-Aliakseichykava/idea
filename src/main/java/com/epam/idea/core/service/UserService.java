package com.epam.idea.core.service;

import com.epam.idea.core.model.User;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public interface UserService extends BaseService<User, Long>, SimpleJpaRepository {

    /**
     * Deletes a user.
     * @param userId The id of the deleted user.
     * @return  The deleted user.
     * @throws com.epam.idea.core.service.exception.UserNotFoundException if no user was found with the given id.
     */
    User deleteById(long userId);

    User update(long userId, User updated);
}
