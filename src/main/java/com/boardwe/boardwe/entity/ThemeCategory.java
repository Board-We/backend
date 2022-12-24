package com.boardwe.boardwe.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThemeCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="theme_category_id")
    private Long id;

    @Column(name="theme_category_name")
    @NotNull
    private String name;

    @Builder
    public ThemeCategory(String name) {
        this.name = name;
    }
}
