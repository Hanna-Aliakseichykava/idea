package com.epam.idea.rest.controller;

import com.epam.idea.core.model.User;
import com.epam.idea.core.model.builders.TestUserBuilder;
import com.epam.idea.core.service.UserService;
import com.epam.idea.core.service.exception.UserNotFoundException;
import com.epam.idea.rest.config.TestConfig;
import com.epam.idea.rest.config.servlet.WebAppConfig;
import com.epam.idea.rest.resource.UserResource;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.epam.idea.core.model.builders.TestUserBuilder.DEFAULT_EMAIL;
import static com.epam.idea.core.model.builders.TestUserBuilder.aUser;
import static com.epam.idea.core.model.builders.TestUserBuilder.anAdmin;
import static com.epam.idea.rest.TestUtils.APPLICATION_JSON_UTF8;
import static com.epam.idea.rest.TestUtils.convertObjectToJsonBytes;
import static com.epam.idea.rest.TestUtils.createStringWithLength;
import static com.epam.idea.rest.resource.UserResource.MAX_LENGTH_EMAIL;
import static com.epam.idea.rest.resource.UserResource.MAX_LENGTH_PASSWORD;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
		//We have to reset our mock between tests because the mock objects
		//are managed by the Spring container. If we would not reset them,
		//stubbing and verified behavior would "leak" from one test to another.
		Mockito.reset(userServiceMock);

		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	@Test
	public void shouldReturnAllFoundUsers() throws Exception {
		User first = aUser().build();
		User second = anAdmin().build();

		when(userServiceMock.findAll()).thenReturn(asList(first, second));

		mockMvc.perform(get("/api/users")
                .accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].email").value(is(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$[0].password").doesNotExist())
                .andExpect(jsonPath("$[0].roles", hasSize(1)))
                .andExpect(jsonPath("$[0].roles[0].name").value(is("USER")))
                .andExpect(jsonPath("$[0].links", hasSize(1)))
                .andExpect(jsonPath("$[0].links[0].rel").value(is("self")))
                .andExpect(jsonPath("$[0].links[0].href").value(is("http://localhost/api/users/1")))
                .andExpect(jsonPath("$[1].email").value(is(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$[1].password").doesNotExist())
                .andExpect(jsonPath("$[1].roles", hasSize(1)))
                .andExpect(jsonPath("$[1].roles[0].name").value(is("ADMIN")))
                .andExpect(jsonPath("$[1].links", hasSize(1)))
                .andExpect(jsonPath("$[1].links[0].href").value(is("http://localhost/api/users/1")));

        verify(userServiceMock, times(1)).findAll();
        verifyNoMoreInteractions(userServiceMock);
	}

	@Test
	public void shouldReturnFoundUserWithHttpCode200() throws Exception {
        User user = TestUserBuilder.aUser().build();
        when(userServiceMock.findOne(1L)).thenReturn(user);

		mockMvc.perform(get("/api/users/{1}", 1L)
                .accept(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email").value(is(DEFAULT_EMAIL)))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles", hasSize(1)))
                .andExpect(jsonPath("$.roles[0].name").value(is("USER")))
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel").value(is("self")))
                .andExpect(jsonPath("$.links[0].href").value(is("http://localhost/api/users/1")));

        verify(userServiceMock, times(1)).findOne(1L);
        verifyNoMoreInteractions(userServiceMock);
	}

    @Test
    public void shouldReturnErrorWithHttpStatus404WhenUserNotFound() throws Exception {
        when(userServiceMock.findOne(1L)).thenThrow(new UserNotFoundException(1L));

        mockMvc.perform(get("/api/users/{1}", 1L)
                .accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logref").value(is("error")))
                .andExpect(jsonPath("$[0].message").value(is("Could not find user with id: 1.")));

        verify(userServiceMock, times(1)).findOne(1L);
        verifyNoMoreInteractions(userServiceMock);
    }

    @Test
    public void shouldReturnValidationErrorsForInvalidEmailAndPassword() throws Exception {
        String invalidEmail = createStringWithLength(MAX_LENGTH_EMAIL + 1);
        String invalidPassword = createStringWithLength(MAX_LENGTH_PASSWORD + 1);

        UserResource userResource = new UserResource();
        userResource.setEmail(invalidEmail);
        userResource.setPassword(invalidPassword);

        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(userResource)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(3)))

                //Spring MVC does not guarantee the ordering of the field errors,
                //i.e. the field errors are returned in random order.
                .andExpect(jsonPath("$[*].logref").value(containsInAnyOrder("password", "email", "email")))
                .andExpect(jsonPath("$[*].message").value(containsInAnyOrder(
                        "length must be between 6 and 20",
                        "length must be between 0 and 20",
                        "not a well-formed email address"
                )));

        verifyZeroInteractions(userServiceMock);
    }

    @Test
    public void shouldCreateUserAndReturnItWithHttpCode201() throws Exception {
        UserResource userResource = new UserResource();
        userResource.setEmail("email@test.com");
        userResource.setPassword("password");
        User saved = new TestUserBuilder()
                .withId(1L)
                .withEmail("email@test.com")
                .withPassword("password")
                .build();

        when(userServiceMock.save(any(User.class))).thenReturn(saved);

        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(userResource)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.email").value(is("email@test.com")))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.links", hasSize(1)))
                .andExpect(jsonPath("$.links[0].rel").value(is("self")))
                .andExpect(jsonPath("$.links[0].href").value(is("http://localhost/api/users/1")));

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServiceMock, times(1)).save(userCaptor.capture());
        verifyNoMoreInteractions(userServiceMock);

        User userArgument = userCaptor.getValue();
        assertThat(userArgument.getEmail()).isEqualTo("email@test.com");
        assertThat(userArgument.getPassword()).isEqualTo("password");
    }
}
