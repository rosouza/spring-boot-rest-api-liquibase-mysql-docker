package com.rosouza.school.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rosouza.school.domain.Student;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A DTO for the {@link com.rosouza.school.domain.Course} entity.
 */
@Data
@NoArgsConstructor
public class CourseDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 50)
    @JsonIgnoreProperties("courses")
    private List<StudentDTO> students = new ArrayList<>();

}
