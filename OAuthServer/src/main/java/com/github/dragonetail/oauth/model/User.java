package com.github.dragonetail.oauth.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        })
})
@Getter
@Setter
public class User implements Serializable {
    private static final long serialVersionUID = -7054738240930572707L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 15)
    private String username;

    @JsonIgnore
    @NotBlank
    @Size(max = 100)
    private String password;

    private boolean accountLocked = false;
    private boolean accountEnabled = true;

    @NotNull
    private LocalDate accountExpired;

    @NotNull
    private LocalDate passwordExpired;

    public User() {
    }

    public User(final String username, final String password) {
        this.username = username;
        this.password = password;

        this.accountLocked = false;
        this.accountEnabled = true;
        this.accountExpired = LocalDate.of(2099, 12, 31);
        this.passwordExpired = this.accountExpired;
    }
}