package com.epam.idea.rest.controller;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.epam.idea.core.model.User;
import com.epam.idea.rest.resource.asm.UserResourceAsm;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.rest.controller.RestErrorHandler.USER_NOT_FOUND_LOGREF;
import static com.epam.idea.util.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.util.TestUtils.convertObjectToJsonBytes;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class UserControllerIntegrationTest {

	public static final long USER_ID = 1L;
	public static final String USER_NAME = "bruceWayne";
	public static final String USER_EMAIL = "bruce@test.com";

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	@DatabaseSetup("userController-users.xml")
	public void shouldReturnInfoOfFoundUserAsJsonWithHttpCode200() throws Exception {

		this.mockMvc.perform(get("/api/v1/users/{userId}", USER_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username").value(USER_NAME))
				.andExpect(jsonPath("$.email").value(USER_EMAIL))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$.links", hasSize(3)))
				.andExpect(jsonPath("$.links[0].rel").value(Link.REL_SELF))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + USER_ID)))
				.andExpect(jsonPath("$.links[1].rel").value(UserResourceAsm.IDEAS_REL))
				.andExpect(jsonPath("$.links[1].href").value(containsString("/api/v1/users/" + USER_ID + "/ideas")))
				.andExpect(jsonPath("$.links[2].rel").value(UserResourceAsm.COMMENTS_REL))
				.andExpect(jsonPath("$.links[2].href").value(containsString("/api/v1/users/" + USER_ID + "/comments")));
	}

	@Test
	@DatabaseSetup("userController-no-users.xml")
	public void shouldReturnErrorMessageAsJsonAndHttpStatus404WhenUserNotFound() throws Exception {

		this.mockMvc.perform(get("/api/v1/users/{userId}", USER_ID)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(USER_NOT_FOUND_LOGREF))
				.andExpect(jsonPath("$[0].message").value("Could not find user with id: " + USER_ID + "."))
				.andExpect(jsonPath("$[0].links", empty()));
	}
}
