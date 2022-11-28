package com.boardwe.boardwe.service;

import org.springframework.core.io.Resource;

public interface FileService {
    Resource loadImageAsResource(String imageUuid);
}
