package com.epam.idea.domain;

public enum RoleType {
    
    ADMIN(1), USER(2);
    
    private final int id;

    RoleType(final int i) {
        id = i;
    }

    public int getId() {
        return id;
    }
}
