package com.effectiveMobile.testTask.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum UserRole implements GrantedAuthority {

    ADMIN("Admin"),
    PERFORMER("Performer");

    private final String name;

    @Override
    public String  getAuthority() {
        return name;
    }
}
