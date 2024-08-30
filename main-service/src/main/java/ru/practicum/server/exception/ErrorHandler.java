package ru.practicum.server.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorBody handleNotFoundException(final NotFoundException e) {
        log.error("Пользователь попытался найти несуществующий объект");
        return new ErrorBody("Такой объект не найден");
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorBody handleJdbcSQLIntegrityConstraintViolationException(final SQLIntegrityConstraintViolationException e) {
        log.error("Пользователь пытался создать конфликтующий объект");
        return new ErrorBody(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorBody handleInvalidDateException(final InvalidDataException e) {
        log.error("Пользователь некорректно ввел даты проведения события");
        return new ErrorBody(e.getMessage());
    }
}
