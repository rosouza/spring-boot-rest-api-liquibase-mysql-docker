package com.rosouza.school.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "t_student")
@Data
@NoArgsConstructor
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false, nullable = false)
    private Long id;

    @Size(max = 100)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @ManyToMany
    @Size(max = 5)
    @JoinTable(
            name = "t_student_course",
            joinColumns = @JoinColumn(name = "student_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "course_id", nullable = false))
    List<Course> courses = new ArrayList<>();

    public List<Course> getCourses() {
        courses.forEach(course -> course.setStudents(new ArrayList<>()));
        return courses;
    }

}
