package com.boardwe.boardwe.exception.custom;

import com.boardwe.boardwe.exception.ErrorCode;

public class ImageInfoNotFoundException extends EntityNotFoundException{
    public ImageInfoNotFoundException() {
        super(ErrorCode.IMAGE_INFO_NOT_FOUND);
    }
}
