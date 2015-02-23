package com.epam.idea.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;


public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersistenceContext.class);
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        System.out.println(dataSource.getClass());
    }
}
