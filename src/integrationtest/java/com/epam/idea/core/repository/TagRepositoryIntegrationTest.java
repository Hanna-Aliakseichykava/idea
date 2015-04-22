package com.epam.idea.core.repository;

import com.epam.idea.annotation.TransactionalIntegrationTest;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionalIntegrationTest
public class TagRepositoryIntegrationTest {

	public static final String TAG_NAME = "Nofilter";

	@Autowired
	private TagRepository tagRepository;

	@Test
	@DatabaseSetup(value = "tagRepository-tags.xml")
	public void shouldReturnCountOfIdeasMarkedByTag() throws Exception {
		// When:
		int ideasCount = tagRepository.getIdeasCountForTag(TAG_NAME);

		// Then:
		assertThat(ideasCount).isEqualTo(2);
	}
}