package org.quentin.security.domain.vo;

public record UserResponse(Integer userId, String username, String email, String phoneNumber) {
}
