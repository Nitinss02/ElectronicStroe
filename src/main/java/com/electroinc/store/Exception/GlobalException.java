package com.electroinc.store.Exception;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.electroinc.store.Dto.ApiResponseMessage;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiResponseMessage> ResourceNotFoundHandler(ResourceNotFound ex) {
        ApiResponseMessage response = ApiResponseMessage.builder().Message(ex.getMessage()).status(HttpStatus.NOT_FOUND)
                .sucess(true).build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        Map<String, Object> response = new HashMap<>();

        allErrors.stream().forEach(objectError -> {
            String message = objectError.getDefaultMessage();
            String field = ((FieldError) objectError).getField();
            response.put(field, message);
        });

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> BadApiRequestHandler(BadApiRequest ex) {
        ApiResponseMessage response = ApiResponseMessage.builder().Message(ex.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .sucess(false).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
