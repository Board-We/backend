package com.boardwe.boardwe.repository;

import com.boardwe.boardwe.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageInfoRepository extends JpaRepository<ImageInfo,Long> {
    Optional<ImageInfo> findByUuid(String imageUuid);
}
