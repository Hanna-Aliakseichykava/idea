package com.epam.idea.core.service.impl;

import java.sql.SQLException;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.builder.model.TestCommentBuilder;
import com.epam.idea.builder.model.TestUserBuilder;
import com.epam.idea.core.model.Comment;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.User;
import com.epam.idea.core.service.CommentService;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.UserService;
import com.epam.idea.util.DbTestUtil;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
@DatabaseSetup("userService/userService-entries.xml")
public class UserServiceImplIntegrationTest {

	@Autowired
	private IdeaService ideaService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@Autowired
	ApplicationContext applicationContext;

	@Test

	@ExpectedDatabase(value = "userService/userService-create-user.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldSaveNewUser() throws Exception {
		User user = new TestUserBuilder().withUsername("user2").withEmail("user2@test.com").withPassword("password2").build();
		userService.save(user);
	}

	@Test
	@ExpectedDatabase(value = "userService/userService-update-user.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldUpdateUser() throws Exception {
		User user = userService.findOne(1L);
		user.setEmail("newmail@test.com");
		user.setUsername("newname");
		user.setPassword("newpassword");
		userService.save(user);
	}

	@Test
	@ExpectedDatabase(value = "userService/userService-delete-user.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldDeleteUser() throws Exception {
		User user = userService.findOne(1L);
		userService.delete(user);
	}

	@After
	public void setUp() throws SQLException {
		userService.findAll();
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "idea");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "tag");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "comment");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "user");
	}
}
