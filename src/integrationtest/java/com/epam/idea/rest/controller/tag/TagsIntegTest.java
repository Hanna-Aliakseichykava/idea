package com.epam.idea.rest.controller.tag;

import com.epam.idea.rest.config.RootConfig;
import com.epam.idea.rest.config.WebAppConfig;
import com.epam.idea.rest.controller.RestErrorHandler;
import com.epam.idea.rest.resource.asm.TagResourceAsm;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.core.repository.config.support.DatabaseConfigProfile.TEST;
import static com.epam.idea.util.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.rest.controller.tag.TagConstants.NAME;
import static com.epam.idea.rest.controller.tag.TagConstants.TAG_ID;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(TEST)
@ContextConfiguration(classes = {RootConfig.class, WebAppConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class})
@WebAppConfiguration
public class TagsIntegTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@DatabaseSetup("tag-entries.xml")
	public void shouldReturnInfoOfFoundTagAsJsonWithHttpCode200() throws Exception {
		mockMvc.perform(get("/api/v1/tags/{tagId}", TAG_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.name").value(is(NAME)))
				.andExpect(jsonPath("$.links", hasSize(2)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/tags/" + TAG_ID)))
				.andExpect(jsonPath("$.links[1].rel").value(is(TagResourceAsm.IDEAS_REL)))
				.andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/tags/" + TAG_ID + "/ideas")));
	}

	@Test
	@DatabaseSetup("no-tag-entries.xml")
	public void shouldReturnErrorMessageAsJsonAndHttpStatus404WhenTagNotFound() throws Exception {
		mockMvc.perform(get("/api/v1/tags/{tagId}", TAG_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound()).andDo(print())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(RestErrorHandler.TAG_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find tag")))
				.andExpect(jsonPath("$[0].links", empty()));
	}
}

