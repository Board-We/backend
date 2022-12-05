package com.boardwe.boardwe.controller;

import com.boardwe.boardwe.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @GetMapping("/image/{imageUuid}")
    public ResponseEntity<Resource> getBoard(@PathVariable String imageUuid) {
        Resource imageResource = fileService.loadImageAsResource(imageUuid);
        return ResponseEntity.ok(imageResource);
    }
}
