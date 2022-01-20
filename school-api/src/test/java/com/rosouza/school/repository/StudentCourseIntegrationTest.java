package com.rosouza.school.repository;

import com.rosouza.school.config.LiquibaseConfiguration;
import com.rosouza.school.domain.Course;
import com.rosouza.school.domain.Student;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

import static com.rosouza.school.StudentTestConstants.SPRING_PROFILE_TEST;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles(SPRING_PROFILE_TEST)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableConfigurationProperties({LiquibaseProperties.class})
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
@Import({LiquibaseConfiguration.class})
class StudentCourseIntegrationTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
        studentRepository.flush();
        courseRepository.deleteAll();
        courseRepository.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.clear();
    }

    @Test
    void should_find_courses_with_no_registrations() {

        var course1 = course();
        course1 = entityManager.persist(course1);

        var student1 = student();
        student1.setCourses(new ArrayList<>());
        student1.getCourses().add(course1);
        entityManager.persist(student1);

        var course2 = course();
        entityManager.persist(course2);

        var course3 = course();
        entityManager.persist(course3);

        var courses = courseRepository.findCourseByStudentsEmpty();
        assertThat(courses).hasSize(2).contains(course2, course3);
    }

    @Test
    void should_find_students_with_no_registrations() {

        var course1 = course();
        course1 = entityManager.persist(course1);

        var student1 = student();
        student1.setCourses(new ArrayList<>());
        student1.getCourses().add(course1);
        entityManager.persist(student1);

        var student2 = student();
        entityManager.persist(student2);

        var student3 = student();
        entityManager.persist(student3);

        var students = studentRepository.findStudentByCoursesEmpty();
        assertThat(students).hasSize(2).contains(student2, student3);
    }

    @Test
    void should_register_max_50_students() {

        var students = new ArrayList<Student>();

        for (int i = 0; i < 51; i++) {
            var student = student();
            student = entityManager.persist(student);
            students.add(student);
        }

        var course1 = course();
        course1.setStudents(students);

        ConstraintViolationException thrown = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(course1);
        });

    }

    @Test
    void should_register_max_5_courses() {

        var courses = new ArrayList<Course>();

        for (int i = 0; i < 10; i++) {
            var course = course();
            course = entityManager.persist(course);
            courses.add(course);
        }

        var student1 = student();
        student1.setCourses(courses);

        ConstraintViolationException thrown = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persistAndFlush(student1);
        });

    }

    Student student() {
        var entity = new Student();
        entity.setName(RandomStringUtils.randomAlphabetic(10));
        return entity;
    }

    Course course() {
        var entity = new Course();
        entity.setName(RandomStringUtils.randomAlphabetic(10));
        return entity;
    }

}
