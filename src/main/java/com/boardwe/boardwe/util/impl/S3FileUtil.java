package com.boardwe.boardwe.util.impl;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.boardwe.boardwe.exception.custom.other.CannotStoreFileException;
import com.boardwe.boardwe.exception.custom.other.UnableToCreateDirectoryException;
import com.boardwe.boardwe.util.FileUtil;
import com.boardwe.boardwe.vo.ImageInfoVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
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
public class S3FileUtil implements FileUtil {

    private final Path rootDir;
    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public ImageInfoVo saveImage(String base64, String fileName) {
        log.info("[S3FileUtil] Upload image to S3.");
        String imageUuid = UUID.randomUUID().toString();
        String imageOriginalName = fileName.substring(0, fileName.lastIndexOf("."));
        String imageExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        String imageSavedName = String.format("%s.%s",imageUuid,imageExtension);
        String imageDir = "images";

        File file = convertBase64(base64, getSavedPath(imageDir, imageSavedName));
        String uploadUrl = upload(file, imageDir + "/" + imageSavedName);

        return ImageInfoVo.builder()
                .uuid(imageUuid)
                .originalName(imageOriginalName)
                .extension(imageExtension)
                .savedName(uploadUrl)
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

    private File convertBase64(String base64, Path savedPath) {
        log.info("[S3FileUtil] Convert base64 img to {}", savedPath.toString());
        File file = savedPath.toFile();

        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            log.error(e.toString());
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
        return file;
    }

    private String upload(File uploadFile, String fileName) {
        String uploadImageUrl = putS3(uploadFile, fileName);

        removeNewFile(uploadFile);  // 로컬에 생성된 File 삭제 (MultipartFile -> File 전환 하며 로컬에 파일 생성됨)

        return uploadImageUrl;      // 업로드된 파일의 S3 URL 주소 반환
    }

    private String putS3(File uploadFile, String fileName) {
        log.info("[S3FileUtil] Upload to S3 {} bucket.", bucket);
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)	// PublicRead 권한으로 업로드 됨
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if(targetFile.delete()) {
            log.info("[S3FileUtil] File is deleted.");
        }else {
            log.info("[S3FileUtil] File cannot be deleted.");
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
