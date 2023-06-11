package org.quentin.security.domain.vo;

import org.quentin.security.domain.enumration.Role;

public record RegisterRequest(String username, String email, String password) {
}
