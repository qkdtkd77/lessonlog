package com.project.lessonlog.security;

import com.project.lessonlog.user.User;
import com.project.lessonlog.user.UserRole;
import lombok.Getter;
import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@NullMarked
public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String email;
    private final @Nullable String password;
    private final UserRole role;
    private final boolean deleted;

    public CustomUserDetails(User user) {
        this.userId = user.getId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.deleted = user.isDeleted();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
        return !deleted;
    }
}
