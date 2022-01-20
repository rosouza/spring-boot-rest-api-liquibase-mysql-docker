package com.rosouza.school.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rosouza.school.domain.Course;
import com.rosouza.school.domain.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A DTO for the {@link com.rosouza.school.domain.Student} entity.
 */
@Data
@NoArgsConstructor
public class StudentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @JsonIgnoreProperties("students")
    @Size(max = 5)
    private List<CourseDTO> courses = new ArrayList<>();

}
