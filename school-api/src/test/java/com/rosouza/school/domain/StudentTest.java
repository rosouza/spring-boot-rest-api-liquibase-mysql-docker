package com.rosouza.school.domain;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static com.rosouza.school.StudentTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StudentTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldNotValidateWhenNameIsNull() {
        shouldNotValidateByProperty("name");
    }

    @Test
    void shouldValidateWhenAllRequiredFieldsArePresent() {
        var student = new Student();

        var constraintViolations = validator.validate(student);
        assertThat(constraintViolations).hasSize(1);

        student.setName(NAME);

        constraintViolations = validator.validate(student);
        assertThat(constraintViolations).hasSize(0);
    }

    ConstraintViolation<Student> find(Set<ConstraintViolation<Student>> constraintViolations, String propertyName) {
        return constraintViolations.stream()
            .filter(i -> propertyName.equals(i.getPropertyPath().toString()))
            .findAny()
            .orElse(null);
    }

    private void shouldNotValidateByProperty(String propertyName) {
        var constraintViolations = validator.validate(new Student());
        ConstraintViolation<Student> violation = find(constraintViolations, propertyName);
        assertNotNull(violation);
        assertThat(violation.getMessage()).isEqualTo("Name is mandatory");
    }

}
