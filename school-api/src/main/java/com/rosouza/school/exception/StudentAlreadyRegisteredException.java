package com.rosouza.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "student.already-registered.error")
public class StudentAlreadyRegisteredException extends RuntimeException {
}
