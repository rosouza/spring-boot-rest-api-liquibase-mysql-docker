package com.rosouza.school.domain;

import com.rosouza.school.service.dto.CourseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_course")
@Data
@NoArgsConstructor
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Size(max = 100)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @ManyToMany(mappedBy = "courses")
    @Size(max=50)
    List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        students.forEach(course -> course.setCourses(new ArrayList<>()));
        return students;
    }

}
