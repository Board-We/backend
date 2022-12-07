package com.boardwe.boardwe.util;

import com.boardwe.boardwe.vo.ImageInfoVo;

import java.nio.file.Path;

public interface FileUtil {
    ImageInfoVo saveImage(String base64, String fileName);

    Path getSavedPath(String savedDir, String savedName);
}
