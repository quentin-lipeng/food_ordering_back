package org.quentin.security.domain.enumration;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    @EnumValue
    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
