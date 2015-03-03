package com.epam.idea.rest.controller;

import com.epam.idea.core.model.Idea;
import com.epam.idea.core.model.builders.TestIdeaBuilder;
import com.epam.idea.core.service.IdeaService;
import com.epam.idea.core.service.exception.IdeaNotFoundException;
import com.epam.idea.rest.config.TestConfig;
import com.epam.idea.rest.config.servlet.WebAppConfig;
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

import static com.epam.idea.core.model.builders.TestIdeaBuilder.DEFAULT_DESCRIPTION;
import static com.epam.idea.core.model.builders.TestIdeaBuilder.DEFAULT_ID;
import static com.epam.idea.core.model.builders.TestIdeaBuilder.DEFAULT_RATING;
import static com.epam.idea.core.model.builders.TestIdeaBuilder.DEFAULT_TITLE;
import static com.epam.idea.core.service.exception.IdeaNotFoundException.ERROR_MSG_PATTERN_IDEA_NOT_FOUND;
import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.rest.controller.RestErrorHandler.IDEA_NOT_FOUND_LOGREF;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class IdeaControllerTest {

	@Autowired
	private IdeaService ideaServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(ideaServiceMock);
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldReturnFoundIdeaWithHttpCode200() throws Exception {
		Idea idea = TestIdeaBuilder.anIdea().build();
		when(ideaServiceMock.findOne(DEFAULT_ID)).thenReturn(idea);

		mockMvc.perform(get("/api/v1/ideas/{ideaId}", DEFAULT_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.title").value(is("Bar")))
				.andExpect(jsonPath("$.description").value(is("Lorem ipsum")))
				.andExpect(jsonPath("$.rating").value(is(DEFAULT_RATING)))
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/ideas/1")));

		verify(ideaServiceMock, times(1)).findOne(DEFAULT_ID);
		verifyNoMoreInteractions(ideaServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenUserNotFound() throws Exception {
		String expectedErrorMsg = String.format(ERROR_MSG_PATTERN_IDEA_NOT_FOUND, DEFAULT_ID);
		when(ideaServiceMock.findOne(DEFAULT_ID)).thenThrow(new IdeaNotFoundException(DEFAULT_ID));

		mockMvc.perform(get("/api/v1/ideas/{ideaId}", DEFAULT_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$[0].logref").value(is(IDEA_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is(expectedErrorMsg)))
				.andExpect(jsonPath("$[0].links", empty()));
		
		verify(ideaServiceMock, times(1)).findOne(DEFAULT_ID);
		verifyNoMoreInteractions(ideaServiceMock);
	}

	@Test
	public void shouldReturnAllFoundIdeas() throws Exception {
		Idea idea = TestIdeaBuilder.anIdea().build();
		when(ideaServiceMock.findAll()).thenReturn(asList(idea));

		mockMvc.perform(get("/api/v1/ideas")
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].title").value(is(DEFAULT_TITLE)))
				.andExpect(jsonPath("$[0].description").value(is(DEFAULT_DESCRIPTION)))
				.andExpect(jsonPath("$[0].rating").value(is(DEFAULT_RATING)))
				.andExpect(jsonPath("$[0].links", hasSize(1)))
				.andExpect(jsonPath("$[0].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/ideas/1")));

		verify(ideaServiceMock, times(1)).findAll();
		verifyNoMoreInteractions(ideaServiceMock);
	}
}
