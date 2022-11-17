package com.boardwe.boardwe.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageInfo {

    @Id
    @GeneratedValue
    @Column(name = "image_info_id")
    private Long id;

    @Column(
            columnDefinition = "NVARCHAR(36)",
            name = "image_uuid"
    )
    @NotNull
    private String uuid;

    @Column(
            columnDefinition = "NVARCHAR(255)",
            name = "image_original_name"
    )
    @NotNull
    private String originalName;

    @Column(
            columnDefinition = "NVARCHAR(255)",
            name = "image_saved_name"
    )
    @NotNull
    private String savedName;

    @Column(
            columnDefinition = "NVARCHAR(6)",
            name = "image_extension"
    )
    @NotNull
    private String extension;

    @Column(
            columnDefinition = "NVARCHAR(255)",
            name = "image_path"
    )
    @NotNull
    private String path;

    @Builder
    public ImageInfo(String uuid, String originalName, String savedName, String extension, String path) {
        this.uuid = uuid;
        this.originalName = originalName;
        this.savedName = savedName;
        this.extension = extension;
        this.path = path;
    }
}
