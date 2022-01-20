package com.rosouza.school.service;

import com.rosouza.school.exception.MaxCoursesRegistrationsException;
import com.rosouza.school.exception.MaxStudentsRegistrationsException;
import com.rosouza.school.exception.StudentAlreadyRegisteredException;
import com.rosouza.school.service.dto.CourseDTO;
import com.rosouza.school.service.dto.StudentDTO;
import com.rosouza.school.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RegistrationService {

    private final Integer MAX_STUDENTS_REGISTRATIONS = 50;
    private final Integer MAX_COURSES_REGISTRATIONS = 5;

    private final StudentService studentService;
    private final CourseService courseService;

    /**
     * Registers a given student into a given course.
     *
     * @param studentId
     * @param courseId
     * @return the updated StudentDTO.
     */
    @Transactional
    public StudentDTO register(Long studentId, Long courseId) {
        var student = studentService.getStudent(studentId);
        validateMaxCoursesRegistrations(student);
        validateAlreadyRegisteredCourse(courseId, student);

        var course = courseService.getCourse(courseId);
        validateMaxStudentsRegistrations(course);

        student.getCourses().add(course);
        return studentService.updateStudent(studentId, student);
    }

    private void validateMaxCoursesRegistrations(StudentDTO student) {
        if (!CollectionUtils.isEmpty(student.getCourses())
                && student.getCourses().size() >= MAX_COURSES_REGISTRATIONS) {
            throw new MaxCoursesRegistrationsException();
        }
    }

    private void validateMaxStudentsRegistrations(CourseDTO course) {
        if (!CollectionUtils.isEmpty(course.getStudents())
                && course.getStudents().size() >= MAX_STUDENTS_REGISTRATIONS) {
            throw new MaxStudentsRegistrationsException();
        }
    }

    private void validateAlreadyRegisteredCourse(Long courseId, StudentDTO student) {
        if (!CollectionUtils.isEmpty(student.getCourses())
                && student.getCourses().stream().map(courseDTO -> courseDTO.getId())
                .collect(Collectors.toList()).contains(courseId)) {
            throw new StudentAlreadyRegisteredException();
        }
    }

}
