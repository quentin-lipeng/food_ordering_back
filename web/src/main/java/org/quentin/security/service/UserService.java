package org.quentin.security.service;

import org.quentin.security.domain.dto.User;
import org.quentin.security.domain.vo.UserResponse;

public interface UserService {
     UserResponse fetchUserWithInfo(String token);
}
