package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.Tag;
import com.epam.idea.core.model.builders.TestIdeaBuilder;
import com.epam.idea.core.model.builders.TestTagBuilder;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.TagService;
import com.epam.idea.core.service.exception.TagDoesNotExistException;
import com.epam.idea.rest.config.TestConfig;
import com.epam.idea.rest.config.WebAppConfig;
import com.epam.idea.rest.resource.asm.TagResourceAsm;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.rest.resource.support.JsonPropertyName.ID;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ihar_Niakhlebau on 18-Mar-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class TagControllerTest {

	@Autowired
	private TagService tagServiceMock;

	@Autowired
	private IdeaService ideaServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(tagServiceMock);
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldReturnAllFoundTags() throws Exception {
		Tag foundTag = TestTagBuilder.aTag().build();
		when(tagServiceMock.findAll()).thenReturn(asList(foundTag));
		mockMvc.perform(get("/api/v1/tags")
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0]." + ID).value(is(((int) foundTag.getId()))))
				.andExpect(jsonPath("$[0].name").value(is(foundTag.getName())))
				.andExpect(jsonPath("$[0].links", hasSize(2)))
				.andExpect(jsonPath("$[0].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/tags/" + foundTag.getId())))
				.andExpect(jsonPath("$[0].links[1].rel").value(is(TagResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$[0].links[1].href").value(containsString("/api/v1/tags/" + foundTag.getId() + "/ideas")));
				verify(tagServiceMock, times(1)).findAll();
		verifyNoMoreInteractions(tagServiceMock);
	}

	@Test
	public void shouldReturnTagByIdWithHttpCode200() throws Exception {
		Tag foundTag = TestTagBuilder.aTag().build();
		when(tagServiceMock.findOne(foundTag.getId())).thenReturn(foundTag);
		mockMvc.perform(get("/api/v1/tags/{tagId}", foundTag.getId())
				.accept(APPLICATION_JSON_UTF8)).andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$." + ID).value(is(((int) foundTag.getId()))))
				.andExpect(jsonPath("$.name").value(is(foundTag.getName())))
				.andExpect(jsonPath("$.links", hasSize(2)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/tags/" + foundTag.getId())))
				.andExpect(jsonPath("$.links[1].rel").value(is(TagResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/tags/" + foundTag.getId() + "/ideas")));

		verify(tagServiceMock, times(1)).findOne(foundTag.getId());
		verifyNoMoreInteractions(tagServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenTagNotFound() throws Exception {
		when(tagServiceMock.findOne(TestTagBuilder.DEFAULT_ID)).thenThrow(new TagDoesNotExistException());
		mockMvc.perform(get("/api/v1/tags/{tagId}", TestTagBuilder.DEFAULT_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound()).andDo(print())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(RestErrorHandler.TAG_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find tag")))
				.andExpect(jsonPath("$[0].links", empty()));

		verify(tagServiceMock, times(1)).findOne(TestTagBuilder.DEFAULT_ID);
		verifyNoMoreInteractions(tagServiceMock);
	}

	@Test
	public void shouldReturnAllFoundIdeasForGivenTag() throws Exception {
		Tag tag = TestTagBuilder.aTag().build();
		Idea idea = TestIdeaBuilder.anIdea().build();
		tag.addIdea(idea);
		idea.addTag(tag);

		when(ideaServiceMock.findIdeasByTagId(tag.getId())).thenReturn(Lists.newArrayList(idea));
		mockMvc.perform(get("/api/v1/tags/{tagId}/ideas", tag.getId())
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title").value(is(idea.getTitle())))
				.andExpect(jsonPath("$[0].description").value(is(idea.getDescription())))
				.andExpect(jsonPath("$[0].rating").value(is(idea.getRating())))
				.andExpect(jsonPath("$[0].tags", hasSize(1)))
				.andExpect(jsonPath("$[0].tags[0]." + ID).value(is(((int) tag.getId()))))
				.andExpect(jsonPath("$[0].tags[0].name").value(is(tag.getName())))
				.andExpect(jsonPath("$[0].tags[0].links", hasSize(2)))
				.andExpect(jsonPath("$[0].tags[0].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[0].tags[0].links[0].href").value(containsString("/api/v1/tags/" + tag.getId())))
				.andExpect(jsonPath("$[0].tags[0].links[1].rel").value(is(TagResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$[0].tags[0].links[1].href").value(containsString("/api/v1/tags/" + tag.getId() + "/ideas")))
				.andExpect(jsonPath("$[0].links", hasSize(1)))
				.andExpect(jsonPath("$[0].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/ideas/" + idea.getId())));

		verify(ideaServiceMock, times(1)).findIdeasByTagId(tag.getId());
		verifyNoMoreInteractions(ideaServiceMock);
	}
}