package com.rosouza.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "course.not-exist.error")
public class CourseIdNotExistException extends RuntimeException {
}
