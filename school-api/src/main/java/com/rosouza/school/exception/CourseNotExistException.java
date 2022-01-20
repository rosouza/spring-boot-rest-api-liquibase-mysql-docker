package com.rosouza.school.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "course.not-exist.error")
public class CourseNotExistException extends RuntimeException {
}
