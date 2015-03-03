package com.epam.idea.rest.config.root;

import com.epam.idea.core.model.User;
import com.epam.idea.core.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
        UserService userService = context.getBean("userServiceImpl", UserService.class);
        User one = userService.findOne(1L);
        System.out.println("a");
        User build = User.getBuilderFrom(one).withEmail("email").withPassword("password")
                .build();
        userService.save(build);
        User one1 = userService.findOne(1L);
        System.out.println("a");
    }
}
