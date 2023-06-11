package org.quentin.security.domain.vo;

import org.quentin.security.domain.dto.User;
import org.quentin.security.domain.enumration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserDetail extends User implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1568630837357691588L;

    private Role role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public UserDetail(Integer userId, String username, String password, Role role, String email) {
        this(userId, username, password, role);
        this.email = email;
    }

    public UserDetail(Integer userId, String username, String password, Role role) {
        super(userId, username, password);
        this.role = role;
    }

    public static UserDetail buildUser(User user) {
        return buildUser(user, null);
    }

    public static UserDetail buildUser(User user, Role role) {
        return buildUser(user, role, null);
    }

    public static UserDetail buildUser(User user, Role role, String email) {

        return Optional.of(new UserDetail(user.getUserId(), user.getUsername(), user.getPassword(), role, email)).orElseThrow();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
