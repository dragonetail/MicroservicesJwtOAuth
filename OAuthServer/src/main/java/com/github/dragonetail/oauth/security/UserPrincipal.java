package com.github.dragonetail.oauth.security;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dragonetail.oauth.model.User;

import lombok.Getter;
import lombok.Setter;

public class UserPrincipal implements UserDetails {
    private static final long serialVersionUID = -394792056682796726L;

    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    @JsonIgnore
    private final boolean accountLocked;
    @JsonIgnore
    private final boolean accountEnabled;
    @JsonIgnore
    private final LocalDate accountExpired;
    @JsonIgnore
    private final LocalDate passwordExpired;

    private final Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    @Setter
    @Getter
    private String clientIp;

    private UserPrincipal(final Long id, final String username,
            final String password, final boolean accountLocked,
            final boolean accountEnabled, final LocalDate accountExpired, final LocalDate passwordExpired,
            final Collection<? extends GrantedAuthority> authorities) {
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.accountLocked = accountLocked;
        this.accountEnabled = accountEnabled;
        this.accountExpired = accountExpired;
        this.passwordExpired = passwordExpired;
        this.authorities = authorities;
    }

    public static UserPrincipal create(final User user) {
        final String[] roleNames = { "USER", "ADMIN" };
        final List<GrantedAuthority> authorities = Arrays.asList(roleNames).stream()
                .map(roleName -> new SimpleGrantedAuthority(roleName)).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.isAccountLocked(),
                user.isAccountEnabled(),
                user.getAccountExpired(),
                user.getPasswordExpired(),
                authorities);
    }

    public Long getId() {
        return this.id;
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
        return this.accountExpired.isAfter(LocalDate.now());
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.passwordExpired.isAfter(LocalDate.now());
    }

    @Override
    public boolean isEnabled() {
        return this.accountEnabled;
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
