package com.hanpyeon.academyapi.exception;

import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(InvalidContentTypeException.class)
    public ResponseEntity<ExceptionResponseBody> invalidContentTypeExceptionHandler(final InvalidContentTypeException invalidContentTypeException) {
        ErrorCode errorCode = ErrorCode.INVALID_CONTENT_TYPE;
        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseBody> requestFormatExceptionHandler(final HttpMessageNotReadableException exception) {
        ErrorCode errorCode = ErrorCode.HTTP_MESSAGE_NOT_READABLE;
        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode, exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponseBody> missingFieldExceptionHandler(final MethodArgumentNotValidException exception) {
        ErrorCode errorCode = ErrorCode.METHOD_ARGUMENT_NOT_VALID_EXCEPTION;
        List<String> errors = exception.getFieldErrors().stream()
                .map(fieldError -> "[ " + fieldError.getField() + " ]" + " : " + fieldError.getDefaultMessage())
                .toList();

        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode, errors));
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ExceptionResponseBody> businessExceptionHandler(final BusinessException businessException) {
        ErrorCode errorCode = businessException.getErrorCode();
        String details = businessException.getMessage();

        return createExceptionResponse(errorCode, ExceptionResponseBody.of(errorCode, details));
    }

    private ResponseEntity<ExceptionResponseBody> createExceptionResponse(ErrorCode errorCode, ExceptionResponseBody exceptionResponseBody) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(exceptionResponseBody);
    }
}
