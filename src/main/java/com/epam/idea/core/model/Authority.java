package com.epam.idea.core.model;

import java.util.Arrays;

public enum Authority {

	ADMIN(1), USER(2);

	private final int id;

	Authority(final int i) {
		id = i;
	}

	public int getId() {
		return id;
	}

	public static Authority getById(int id) {
		return Arrays.stream(values())
				.filter(v -> v.getId() == id)
				.findAny().orElse(null);
	}
}
