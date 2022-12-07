package com.boardwe.boardwe.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageInfoVo {
    private String uuid;
    private String originalName;
    private String extension;
    private String savedName;
    private String path;
}
