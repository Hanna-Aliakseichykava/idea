package com.epam.idea.web.controller;

import com.epam.idea.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class IndexControllerTest {
	
	@InjectMocks
	IndexController indexController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
	}

	@Test
	public void testName() throws Exception {

		ObjectMapper mapper = new ObjectMapper( );
		String userAsString = mapper.writeValueAsString(User.getBuilder()
				.withPassword("pass")
				.withEmail("email")
				.build());

		mockMvc.perform(post("/test")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userAsString))
				.andExpect(jsonPath("$.password", is("pass")))
				.andDo(print());
	}
}
