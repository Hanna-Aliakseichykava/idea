package com.epam.idea.core.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class AuthorityTest {

	@Test
	public void shouldReturnAdmin() throws Exception {
		// Given:
		int adminId = 1;

		//When:
		Authority actual = Authority.getById(adminId);

		//Then:
		assertThat(actual).isEqualTo(Authority.ADMIN);
	}

	@Test
	public void shouldReturnNullWhenThereAreNoAuthorityWithTheGivenId() throws Exception {
		//Given:
		int invalidId = -2;
		
		//When:
		Authority actual = Authority.getById(invalidId);
		
		//Then:
		assertThat(actual).isNull();
	}
}
