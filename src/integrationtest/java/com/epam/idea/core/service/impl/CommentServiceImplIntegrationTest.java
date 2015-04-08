package com.epam.idea.core.service.impl;

import java.sql.SQLException;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.builder.model.TestCommentBuilder;
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
@DatabaseSetup("commentService/commentService-entries.xml")
public class CommentServiceImplIntegrationTest {

	@Autowired
	private IdeaService ideaService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	@ExpectedDatabase(value = "commentService/commentService-create-comment.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldSaveNewComment() throws Exception {
		Comment comment = new TestCommentBuilder().withBody("Cool").withRating(9).build();
		User user = userService.findOne(1L);
		Idea idea = ideaService.findOne(2L);
		comment.setAuthor(user);
		comment.setSubject(idea);
		commentService.save(comment);
	}

	@Test
	@ExpectedDatabase(value = "commentService/commentService-update-comment.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldUpdateComment() throws Exception {
		Comment comment = commentService.findOne(3L);
		comment.setBody("Bad");
		comment.setRating(2);
		commentService.save(comment);
	}

	@Test
	@ExpectedDatabase(value = "commentService/commentService-delete-comment.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldDeleteComment() throws Exception {
		Comment comment = commentService.findOne(3L);
		commentService.delete(comment);
	}

	@After
	public void setUp() throws SQLException {
		commentService.findAll();
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "idea");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "tag");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "comment");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "user");
	}
}
