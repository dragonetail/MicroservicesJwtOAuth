package com.github.dragonetail.service2.model;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSkill implements Serializable {
    private static final long serialVersionUID = -1020254625582845295L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String description;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private SkillLevel level;

    public UserSkill(@NotNull final Long userId, @NotBlank @Size(max = 40) final String name,
            @NotBlank @Size(max = 255) final String description, @NotNull final SkillLevel level) {
        super();
        this.userId = userId;
        this.name = name;
        this.description = description;
        this.level = level;
    }

}