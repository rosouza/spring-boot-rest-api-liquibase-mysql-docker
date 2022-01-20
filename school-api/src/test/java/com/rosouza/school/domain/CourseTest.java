package com.rosouza.school.domain;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.rosouza.school.CourseTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CourseTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotValidateWhenNameIsNull() {
        shouldNotValidateByProperty("name");
    }

    @Test
    void shouldValidateWhenAllRequiredFieldsArePresent() {
        var course = new Course();

        var constraintViolations = validator.validate(course);
        assertThat(constraintViolations).hasSize(1);

        course.setName(NAME);

        constraintViolations = validator.validate(course);
        assertThat(constraintViolations).hasSize(0);
    }

    ConstraintViolation<Course> find(Set<ConstraintViolation<Course>> constraintViolations, String propertyName) {
        return constraintViolations.stream()
            .filter(i -> propertyName.equals(i.getPropertyPath().toString()))
            .findAny()
            .orElse(null);
    }

    private void shouldNotValidateByProperty(String propertyName) {
        var constraintViolations = validator.validate(new Course());
        ConstraintViolation<Course> violation = find(constraintViolations, propertyName);
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("Name is mandatory");
    }

}
