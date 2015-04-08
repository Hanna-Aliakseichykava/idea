package com.epam.idea.core.service.impl;

import java.sql.SQLException;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.builder.model.TestTagBuilder;
import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.UserService;
import com.epam.idea.util.DbTestUtil;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Ihar_Niakhlebau on 07-Apr-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
@DatabaseSetup("tagService/tagService-entries.xml")
public class TagServiceImplIntegrationTest {

	@Autowired
	TagService tagService;

	@Autowired
	IdeaService ideaService;

	@Autowired
	UserService userService;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	@Ignore
	@ExpectedDatabase(value = "tagService/tagService-create-tag.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldSaveNewTag() throws Exception {
		Tag tag = new TestTagBuilder().withName("Dota").build();
		Idea idea = ideaService.findOne(1L);
		idea.setRelatedTags(Lists.newArrayList(tag));
		tagService.save(tag);
		ideaService.findOne(1L).getRelatedTags().size();
	}

	@Test
	@ExpectedDatabase(value = "tagService/tagService-update-tag.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldUpdateTag() throws Exception {
		Tag tag = tagService.findOne(1L);
		tag.setName("WoW");
		tagService.save(tag);

		assertThat(tag.getName()).isEqualTo(tagService.findOne(1L).getName());
	}

	@Test
	@ExpectedDatabase(value = "tagService/tagService-delete-tag.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
	public void shouldDeleteTag() throws Exception {
		Tag tag = tagService.findOne(1L);
		tagService.delete(tag);
	}

	@After
	public void setUp() throws SQLException {
		tagService.findAll();
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "idea");
		DbTestUtil.resetAutoIncrementColumns(applicationContext, "tag");
	}
}
