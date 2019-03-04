package com.github.dragonetail.service1.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = -394792056682796726L;

    @Getter
    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password = "******";

    private final Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    @Setter
    @Getter
    private String clientIp;

    public UserPrincipal(final Long id) {
        super();
        this.id = id;
        this.username = id.toString();

        final String[] roleNames = { "USER", "ADMIN" };
        final List<GrantedAuthority> authorities = Arrays.asList(roleNames).stream()
                .map(roleName -> new SimpleGrantedAuthority(roleName)).collect(Collectors.toList());
        this.authorities = authorities;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        final UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
}
