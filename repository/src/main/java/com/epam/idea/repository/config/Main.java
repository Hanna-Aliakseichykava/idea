package com.epam.idea.repository.config;

import com.epam.idea.domain.User;
import com.epam.idea.repository.UserRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceConfig.class);
        UserRepository bean = context.getBean(UserRepository.class);
        Optional<User> one = bean.findOne(1L);
        if (one.isPresent()) {
            User user = one.get();
            System.out.println(user);
        }
    }
}
