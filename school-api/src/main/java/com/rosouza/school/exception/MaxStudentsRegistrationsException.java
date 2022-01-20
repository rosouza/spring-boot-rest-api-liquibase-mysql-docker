package com.rosouza.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "courses.max-students-registrations.error")
public class MaxStudentsRegistrationsException extends RuntimeException {
}
