package com.epam.idea.rest.controller;

import com.epam.idea.rest.config.RootConfig;
import com.epam.idea.rest.config.WebAppConfig;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigWebContextLoader.class, classes = {RootConfig.class, WebAppConfig.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
@ActiveProfiles("dev")
@DatabaseSetup("ideaData.xml")
public class IdeaControllerTestIntegTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
	}

	IDataSet buildDataSet() throws DataSetException {
		DataSetBuilder builder = new DataSetBuilder();
		builder.newRow("PERSON").with(NAME, "Bob").with(LAST_NAME, "Doe").with(AGE, 18).add();
		builder.newRow("PERSON").with(NAME, "Alice").with(LAST_NAME, "Foo").with(AGE, 23).add();
		builder.newRow("PERSON").with(NAME, "Charlie").with(LAST_NAME, "Brown").with(AGE, 42).add();
		return builder.build();
	}
	
	@Test
	@ExpectedDatabase("toDoData.xml")
	public void findAll() throws Exception {
		mockMvc.perform(get("/api/todo"))
				.andExpect(status().isOk())
				.andExpect(content().mimeType(IntegrationTestUtil.APPLICATION_JSON_UTF8))
				.andExpect(content().string("[{\"id\":1,\"description\":\"Lorem ipsum\",\"title\":\"Foo\"},{\"id\":2,\"description\":\"Lorem ipsum\",\"title\":\"Bar\"}]"));
}
