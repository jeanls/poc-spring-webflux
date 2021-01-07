package com.jean.store.exceptions;

import com.jean.store.dtos.ErrorDto;
import com.jean.store.dtos.WrapperErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {ServerException.class})
    public ResponseEntity<Object> handleServerException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {
        final WrapperErrorDto wrapperErrorDto = ex.getErrorDto();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(wrapperErrorDto);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        final List<ErrorDto> errors = new ArrayList<>();
//
//        for (final FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
//            errors.add(new ErrorDto(fieldError.getField(), fieldError.getDefaultMessage()));
//        }
//
//        for (final ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(new ErrorDto(objectError.getObjectName(), objectError.getDefaultMessage()));
//        }
//
//        return ResponseEntity.badRequest().body(new WrapperErrorDto("Erro de validação", errors));
//    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleAnyException(Exception ex) {
        log.error("handleAnyException", ex);
        String errorMessageDescription = ex.getLocalizedMessage();
        if (errorMessageDescription == null) {
            errorMessageDescription = ex.toString();
        }
        return new ResponseEntity<>(errorMessageDescription, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
