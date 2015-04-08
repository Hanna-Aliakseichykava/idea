package com.epam.idea.core.service.impl;

import java.sql.SQLException;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.builder.model.TestIdeaBuilder;
import com.epam.idea.builder.model.TestTagBuilder;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.TagService;
import com.epam.idea.util.DbTestUtil;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
@DatabaseSetup("ideaService/ideaService-entries.xml")
public class IdeaServiceImplIntegrationTest {

	@Autowired
	private IdeaService ideaService;

	@Autowired
	private TagService tagService;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	@ExpectedDatabase(value = "ideaService/ideaService-create-idea.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldSaveNewIdea() throws Exception {
		Tag tag = tagService.findOne(1L);
		Idea idea = new TestIdeaBuilder()
				.withDescription("new description")
				.withTitle("new title")
//				.addTag(tag)
				.build();

		idea.setRelatedTags(Lists.newArrayList(tag));
		Idea saveForUser = ideaService.saveForUser(1L, idea);
		assertThat(saveForUser.getId()).isEqualTo(2L);
	}
//
	@Test
	@ExpectedDatabase(value = "ideaService/ideaService-update-idea.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldUpdateIdea() throws Exception {
		Tag tag = new TestTagBuilder().withName("Dota").build();
		tagService.save(tag);
		Idea idea = ideaService.findOne(1L);
		idea.setDescription("new description");
		idea.setTitle("new title");
		idea.setRelatedTags(Lists.newArrayList(tag));
		ideaService.save(idea);
//		assertThat(ideaService.findAll().size()).isEqualTo(1);
	}

	@Test
	@ExpectedDatabase(value = "ideaService/ideaService-delete-idea.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldDeleteIdea() throws Exception {
		Idea idea = ideaService.findOne(1L);
		ideaService.delete(idea);
		assertThat(ideaService.findAll().size()).isEqualTo(0);
	}

	@Test
	@ExpectedDatabase(value = "ideaService/ideaService-delete-idea.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldDeleteIdeaById() throws Exception {
		ideaService.deleteById(1L);
		assertThat(ideaService.findAll().size()).isEqualTo(0);
	}

	@After
	public void setUp() throws SQLException {
		ideaService.findAll();
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "idea");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "tag");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "comment");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "user");
	}
}
