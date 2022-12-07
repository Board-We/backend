package com.boardwe.boardwe.exception.custom.entity;

import com.boardwe.boardwe.exception.ErrorCode;
import com.boardwe.boardwe.exception.custom.entity.EntityNotFoundException;

public class ImageInfoNotFoundException extends EntityNotFoundException {
    public ImageInfoNotFoundException() {
        super(ErrorCode.IMAGE_INFO_NOT_FOUND);
    }
}
