package com.jean.store.exceptions;

import com.jean.store.dtos.WrapperErrorDto;
import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private WrapperErrorDto errorDto;

    public BadRequestException(WrapperErrorDto wrapperErrorDto) {
        super(wrapperErrorDto.getMessage());
        this.errorDto = wrapperErrorDto;
    }

    public BadRequestException(String message) {
        super(message);
        this.errorDto = new WrapperErrorDto(message, null);
    }
}
