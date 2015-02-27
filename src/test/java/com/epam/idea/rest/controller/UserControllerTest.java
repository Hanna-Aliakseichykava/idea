package com.epam.idea.rest.controller;

import com.epam.idea.core.model.User;
import com.epam.idea.core.model.builders.TestUserBuilder;
import com.epam.idea.core.service.UserService;
import com.epam.idea.rest.config.TestConfig;
import com.epam.idea.rest.config.servlet.WebAppConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.ZonedDateTime;

import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class UserControllerTest {

	@InjectMocks
	private UserController userController;

	@Autowired
	private UserService userServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		//We have to reset our mock between tests because the mock objects
		//are managed by the Spring container. If we would not reset them,
		//stubbing and verified behavior would "leak" from one test to another.
		Mockito.reset(userServiceMock);

		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldReturnFoundUsers() throws Exception {
		User first = TestUserBuilder.anUser().build();
		User second = TestUserBuilder.anAdmin().build();

		when(userServiceMock.findAll()).thenReturn(asList(first, second));

		mockMvc.perform(get("/api/users").contentType(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)));
		
	}

	@Test
	public void shouldReturnUserWithCodeOk() throws Exception {
//		User returnedUser = User.getBuilder()
//				.withEmail("email")
//				.withPassword("password")
//				.build();
		User returnedUser = TestUserBuilder.anAdmin().withCreationTime(ZonedDateTime.now()).build();
		when(userServiceMock.findOne(1L)).thenReturn(returnedUser);
		mockMvc.perform(get("/api/users/{1}", 1L))
				.andDo(print())
				.andExpect(status().isOk());
//				.andExpect(jsonPath("$.email").value("email"))
//				.andExpect(jsonPath("$.password").doesNotExist());
	}
}
