package com.epam.idea.rest.controller;

import com.epam.idea.core.model.User;
import com.epam.idea.core.model.builders.TestUserBuilder;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.rest.config.TestConfig;
import com.epam.idea.rest.config.WebAppConfig;
import com.epam.idea.rest.resource.UserResource;
import com.epam.idea.rest.resource.builders.TestUserResourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.core.model.User.MAX_LENGTH_EMAIL;
import static com.epam.idea.core.model.User.MAX_LENGTH_PASSWORD;
import static com.epam.idea.core.model.User.MAX_LENGTH_USERNAME;
import static com.epam.idea.core.model.builders.TestUserBuilder.DEFAULT_USER_ID;
import static com.epam.idea.core.model.builders.TestUserBuilder.aUser;
import static com.epam.idea.core.model.builders.TestUserBuilder.anAdmin;
import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.rest.TestUtils.EMPTY;
import static com.epam.idea.rest.TestUtils.convertObjectToJsonBytes;
import static com.epam.idea.rest.TestUtils.createEmailWithLength;
import static com.epam.idea.rest.TestUtils.createStringWithLength;
import static com.epam.idea.rest.controller.RestErrorHandler.USER_NOT_FOUND_LOGREF;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebAppConfig.class})
@WebAppConfiguration
public class UserControllerTest {

	@Autowired
	private UserService userServiceMock;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		Mockito.reset(userServiceMock);

		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldReturnAllFoundUsers() throws Exception {
		User first = aUser().build();
		User second = anAdmin().build();

		when(userServiceMock.findAll()).thenReturn(asList(first, second));

		mockMvc.perform(get("/api/v1/users")
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].username").value(is(first.getUsername())))
				.andExpect(jsonPath("$[0].email").value(is(first.getEmail())))
				.andExpect(jsonPath("$[0].password").doesNotExist())
				.andExpect(jsonPath("$[0].links", hasSize(1)))
				.andExpect(jsonPath("$[0].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[0].links[0].href").value(containsString("/api/v1/users/" + first.getId())))
				.andExpect(jsonPath("$[1].username").value(is(second.getUsername())))
				.andExpect(jsonPath("$[1].email").value(is(second.getEmail())))
				.andExpect(jsonPath("$[1].password").doesNotExist())
				.andExpect(jsonPath("$[1].links", hasSize(1)))
				.andExpect(jsonPath("$[1].links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$[1].links[0].href").value(containsString("/api/v1/users/" + second.getId())));

		verify(userServiceMock, times(1)).findAll();
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnFoundUserWithHttpCode200() throws Exception {
		User user = TestUserBuilder.aUser().build();
		when(userServiceMock.findOne(user.getId())).thenReturn(user);

		mockMvc.perform(get("/api/v1/users/{userId}", DEFAULT_USER_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username").value(is(user.getUsername())))
				.andExpect(jsonPath("$.email").value(is(user.getEmail())))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + user.getId())));

		verify(userServiceMock, times(1)).findOne(1L);
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenUserNotFound() throws Exception {
		when(userServiceMock.findOne(DEFAULT_USER_ID)).thenThrow(new UserNotFoundException(DEFAULT_USER_ID));

		mockMvc.perform(get("/api/v1/users/{userId}", DEFAULT_USER_ID)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(USER_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find user with id: 1.")));

		verify(userServiceMock, times(1)).findOne(DEFAULT_USER_ID);
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnValidationErrorsForTooLongEmailAndPassword() throws Exception {
		String invalidUsername = createStringWithLength(MAX_LENGTH_USERNAME + 1);
		String invalidEmail = createEmailWithLength(MAX_LENGTH_EMAIL + 1);
		String invalidPassword = createStringWithLength(MAX_LENGTH_PASSWORD + 1);

		UserResource userResource = TestUserResourceBuilder.aUserResource()
				.withUsername(invalidUsername)
				.withEmail(invalidEmail)
				.withPassword(invalidPassword)
				.build();

		mockMvc.perform(post("/api/v1/users")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(userResource)))
				.andExpect(status().isBadRequest())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(3)))

						//Spring MVC does not guarantee the ordering of the field errors,
						//i.e. the field errors are returned in random order.
				.andExpect(jsonPath("$[*].logref").value(containsInAnyOrder("password", "email", "username")))
				.andExpect(jsonPath("$[*].message").value(containsInAnyOrder(
						String.format("size must be between %s and %s", User.MIN_LENGTH_EMAIL, User.MAX_LENGTH_EMAIL),
						String.format("size must be between %s and %s", User.MIN_LENGTH_PASSWORD, User.MAX_LENGTH_PASSWORD),
						String.format("size must be between %s and %s", User.MIN_LENGTH_USERNAME, User.MAX_LENGTH_USERNAME)
				)));

		verifyZeroInteractions(userServiceMock);
	}

	@Test
	public void shouldCreateUserAndReturnItWithHttpCode201() throws Exception {
		String username = "username";
		String email = "email@test.com";
		String password = "password";
		UserResource userResource = TestUserResourceBuilder.aUserResource()
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.build();
		User saved = new TestUserBuilder()
				.withId(DEFAULT_USER_ID)
				.withUsername(username)
				.withEmail(email)
				.withPassword(password)
				.build();

		when(userServiceMock.save(any(User.class))).thenReturn(saved);

		mockMvc.perform(post("/api/v1/users")
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(userResource)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username").value(is(saved.getUsername())))
				.andExpect(jsonPath("$.email").value(is(saved.getEmail())))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + saved.getId())));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userServiceMock, times(1)).save(userCaptor.capture());
		verifyNoMoreInteractions(userServiceMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(userResource.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(userResource.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(userResource.getPassword());
	}

	@Test
	public void shouldDeleteUserAndReturnHttpCode200() throws Exception {
		User deleted = TestUserBuilder.aUser().build();

		when(userServiceMock.deleteById(deleted.getId())).thenReturn(deleted);

		mockMvc.perform(delete("/api/v1/users/{userId}", deleted.getId())
				.contentType(APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(is(EMPTY)));

		verify(userServiceMock, times(1)).deleteById(deleted.getId());
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenDeleteUserWhichDoesNotExist() throws Exception {
		long userId = 3L;
		when(userServiceMock.deleteById(userId)).thenThrow(new UserNotFoundException(userId));

		mockMvc.perform(delete("/api/v1/users/{userId}", userId)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(USER_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find user with id: " + userId + ".")))
				.andExpect(jsonPath("$[0].links", hasSize(0)));

		verify(userServiceMock, times(1)).deleteById(userId);
		verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldUpdateUserAndReturnItWithHttpCode200() throws Exception {
		long userId = 2L;
		String newUsername = "new_username";
		String newEmail = "new_email@test.com";
		String newPassword = "new_password";
		UserResource source = TestUserResourceBuilder.aUserResource()
				.withUsername(newUsername)
				.withEmail(newEmail)
				.withPassword(newPassword)
				.build();
		User updated = new TestUserBuilder()
				.withId(userId)
				.withUsername(newUsername)
				.withEmail(newEmail)
				.withPassword(newPassword)
				.build();

		when(userServiceMock.update(eq(userId), any(User.class))).thenReturn(updated);

		mockMvc.perform(put("/api/v1/users/{userId}", userId)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(source)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username").value(is(updated.getUsername())))
				.andExpect(jsonPath("$.email").value(is(updated.getEmail())))
				.andExpect(jsonPath("$.password").doesNotExist())
				.andExpect(jsonPath("$.links", hasSize(1)))
				.andExpect(jsonPath("$.links[0].rel").value(is(Link.REL_SELF)))
				.andExpect(jsonPath("$.links[0].href").value(containsString("/api/v1/users/" + updated.getId())));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userServiceMock, times(1)).update(eq(userId), userCaptor.capture());
		verifyNoMoreInteractions(userServiceMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(source.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(source.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(source.getPassword());
	}

	@Test
	public void shouldReturnErrorWithHttpStatus404WhenUpdateUserWhichDoesNotExist() throws Exception {
		long userId = 3L;
		UserResource source = TestUserResourceBuilder.aUserResource()
				.withUsername("new_username")
				.withEmail("new_email@test.com")
				.withPassword("new_password")
				.build();

		when(userServiceMock.update(eq(userId), any(User.class))).thenThrow(new UserNotFoundException(userId));

		mockMvc.perform(put("/api/v1/users/{userId}", userId)
				.contentType(APPLICATION_JSON_UTF8)
				.accept(APPLICATION_JSON_UTF8)
				.content(convertObjectToJsonBytes(source)))
				.andExpect(status().isNotFound())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].logref").value(is(USER_NOT_FOUND_LOGREF)))
				.andExpect(jsonPath("$[0].message").value(is("Could not find user with id: " + userId + ".")))
				.andExpect(jsonPath("$[0].links", hasSize(0)));

		ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
		verify(userServiceMock, times(1)).update(eq(userId), userCaptor.capture());
		verifyNoMoreInteractions(userServiceMock);

		User userArgument = userCaptor.getValue();
		assertThat(userArgument.getUsername()).isEqualTo(source.getUsername());
		assertThat(userArgument.getEmail()).isEqualTo(source.getEmail());
		assertThat(userArgument.getPassword()).isEqualTo(source.getPassword());
	}
}
