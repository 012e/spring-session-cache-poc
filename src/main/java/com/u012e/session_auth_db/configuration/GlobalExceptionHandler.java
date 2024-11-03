package com.u012e.session_auth_db.configuration;

import com.u012e.session_auth_db.exception.InvalidCredentialsException;
import com.u012e.session_auth_db.utils.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<GenericResponse<HashMap<String, String>>> handleInvalidArgument(InvalidCredentialsException exception) {
        var data = GenericResponse.<HashMap<String, String>>builder()
                .message(exception.getMessage())
                .success(false)
                .build();
        return new ResponseEntity<>(data, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<GenericResponse<HashMap<String, String>>> handleInvalidArgument(IllegalArgumentException exception) {
        var data = GenericResponse.<HashMap<String, String>>builder()
                .message(exception.getMessage())
                .success(false)
                .build();
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<HashMap<String, String>>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> map =  new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        var data = GenericResponse.<HashMap<String, String>>builder()
                .message("Invalid arguments")
                .data((HashMap<String, String>) map)
                .success(false)
                .build();
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
