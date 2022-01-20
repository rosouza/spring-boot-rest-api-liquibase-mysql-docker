package com.rosouza.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "student.max-courses-registrations.error")
public class MaxCoursesRegistrationsException extends RuntimeException {
}
