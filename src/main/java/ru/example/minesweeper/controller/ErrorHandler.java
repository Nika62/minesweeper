package ru.example.minesweeper.controller;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.example.minesweeper.exception.NotFoundException;
import ru.example.minesweeper.exception.ValidationException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ErrorHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler
    public ErrorResponse handleConflictException(final NotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleValidationException(final ValidationException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler
    public ErrorResponse handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return new ErrorResponse(e.getMessage());
    }

    public class ErrorResponse {
        String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
}

