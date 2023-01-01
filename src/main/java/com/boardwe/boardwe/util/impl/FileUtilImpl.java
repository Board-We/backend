package com.boardwe.boardwe.util.impl;

import com.boardwe.boardwe.exception.custom.other.CannotStoreFileException;
import com.boardwe.boardwe.exception.custom.other.UnableToCreateDirectoryException;
import com.boardwe.boardwe.util.FileUtil;
import com.boardwe.boardwe.vo.ImageInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileUtilImpl implements FileUtil {

    private final Path rootDir;

    @Override
    public ImageInfoVo saveImage(String base64, String fileName) {
        log.info("[FileUtilImpl] Save image.");
        String imageUuid = UUID.randomUUID().toString();
        String imageOriginalName = fileName.substring(0, fileName.lastIndexOf("."));
        String imageExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String imageSavedName = String.format("%s.%s",imageUuid,imageExtension);
        String imageDir = "/images";

        saveBase64(base64, getSavedPath(imageDir, imageSavedName));

        return ImageInfoVo.builder()
                .uuid(imageUuid)
                .originalName(imageOriginalName)
                .extension(imageExtension)
                .savedName(imageSavedName)
                .path(imageDir)
                .build();
    }

    @Override
    public Path getSavedPath(String savedDir, String savedName) {
        if (!StringUtils.hasText(savedDir)){
            return rootDir.resolve(savedName);
        }
        if (savedDir.startsWith("/")){
            savedDir = savedDir.substring(1);
        }
        if (!savedDir.endsWith("/")) {
            savedDir = savedDir + "/";
        }
        Path targetDir = rootDir.resolve(savedDir);
        createDirectory(targetDir);
        return targetDir.resolve(savedName);
    }

    private void saveBase64(String base64, Path savedPath) {
        File file = savedPath.toFile();
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            file.delete();
            throw new CannotStoreFileException();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createDirectory(Path targetDir) {
        try {
            Files.createDirectories(targetDir);
        } catch (IOException e) {
            throw new UnableToCreateDirectoryException();
        }
    }
}
