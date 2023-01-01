package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.entity.ImageInfo;
import com.boardwe.boardwe.exception.custom.entity.ImageInfoNotFoundException;
import com.boardwe.boardwe.exception.custom.other.ImageNotFoundException;
import com.boardwe.boardwe.repository.ImageInfoRepository;
import com.boardwe.boardwe.service.FileService;
import com.boardwe.boardwe.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ImageInfoRepository imageInfoRepository;

    private final FileUtil fileUtil;

    @Override
    public Resource loadImageAsResource(String imageUuid) {
        log.info("[FileServiceImpl] Load image {}.", imageUuid);
        ImageInfo imageInfo = imageInfoRepository.findByUuid(imageUuid)
                .orElseThrow(ImageInfoNotFoundException::new);

        Path filePath = fileUtil.getSavedPath(imageInfo.getPath(), imageInfo.getSavedName());
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
}
