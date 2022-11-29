package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.entity.ImageInfo;
import com.boardwe.boardwe.exception.custom.ImageInfoNotFoundException;
import com.boardwe.boardwe.exception.custom.ImageNotFoundException;
import com.boardwe.boardwe.repository.ImageInfoRepository;
import com.boardwe.boardwe.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final Path rootDir;

    private final ImageInfoRepository imageInfoRepository;

    @Override
    public Resource loadImageAsResource(String imageUuid) {
        ImageInfo imageInfo = imageInfoRepository.findByUuid(imageUuid)
                .orElseThrow(ImageInfoNotFoundException::new);

        Path filePath = getTargetDir(imageInfo.getPath(), imageInfo.getSavedName());
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ImageNotFoundException();
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private Path getTargetDir(String path, String savedName) {
        if (!StringUtils.hasText(path)){
            return rootDir.resolve(savedName);
        }
        if (path.startsWith("/")){
            path = path.substring(1);
        }
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        return rootDir.resolve(path + savedName);
    }
}
