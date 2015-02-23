package com.epam.idea.domain;

public enum Authority {
    
    ADMIN(1), USER(2);
    
    private final int id;

    Authority(final int i) {
        id = i;
    }

    public int getId() {
        return id;
    }
}
