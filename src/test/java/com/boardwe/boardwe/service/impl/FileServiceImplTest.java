package com.boardwe.boardwe.service.impl;

import com.boardwe.boardwe.entity.ImageInfo;
import com.boardwe.boardwe.repository.ImageInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;

import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private ImageInfoRepository imageInfoRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    private final String ROOT_DIR = "./src/test/resources/files";
    private final String ORIGINAL_FILE_DIR = "existing";
    private Path storedPath;

    @BeforeEach
    void before(){
        storedPath = Path.of(ROOT_DIR);
        fileService = new FileServiceImpl(
                storedPath,
                imageInfoRepository);
    }

    @Test
    @DisplayName("Path 정보가 존재하는 이미지를 조회하는 데 성공한다.")
    void load_image_as_resource() throws Exception {
        // given
        String imageUuid = "1234";
        String imageName = "testImage";
        String extension = "jpg";
        String savedName = String.format("%s.%s", imageName, extension);

        when(imageInfoRepository.findByUuid(imageUuid))
                .thenReturn(Optional.of(
                        ImageInfo.builder()
                        .uuid(imageUuid)
                        .originalName(imageName)
                        .extension(extension)
                        .path(ORIGINAL_FILE_DIR)
                        .savedName(savedName)
                        .build()));

        // when
        Resource result = fileService.loadImageAsResource(imageUuid);

        // then
        assertEquals(savedName, result.getFilename());

    }

    @Test
    @DisplayName("루트 폴더에 저장된 이미지를 조회하는 데 성공한다.")
    void load_image_as_resource_with_no_path() throws Exception {
        // given
        String imageUuid = "1234";
        String imageName = "testImage";
        String extension = "jpg";
        String savedName = String.format("%s.%s", imageName, extension);

        when(imageInfoRepository.findByUuid(imageUuid))
                .thenReturn(Optional.of(
                        ImageInfo.builder()
                        .uuid(imageUuid)
                        .originalName(imageName)
                        .extension(extension)
                        .savedName(savedName)
                        .build()));

        // when
        Resource result = fileService.loadImageAsResource(imageUuid);

        // then
        assertEquals(savedName, result.getFilename());

    }

}