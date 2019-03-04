package com.github.dragonetail.service1.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.github.dragonetail.service2.model.UserSkill;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_details")
@Getter
@Setter
@NoArgsConstructor
public class UserDetails implements Serializable {
    private static final long serialVersionUID = 2316146815362335774L;

    @Id
    private Long userId;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank
    @Size(max = 40)
    private String city;

    @NotBlank
    @Size(max = 40)
    private String country;

    @Transient
    private List<UserSkill> skills;

    public UserDetails(final Long userId, @NotBlank @Size(max = 40) final String name,
            @NotBlank @Size(max = 100) @Email final String email, @NotBlank @Size(max = 40) final String city,
            @NotBlank @Size(max = 40) final String country) {
        super();
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.city = city;
        this.country = country;
    }

}