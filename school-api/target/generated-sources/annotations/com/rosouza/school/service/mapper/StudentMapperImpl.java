package com.rosouza.school.service.mapper;

import com.rosouza.school.domain.Course;
import com.rosouza.school.domain.Student;
import com.rosouza.school.service.dto.CourseDTO;
import com.rosouza.school.service.dto.StudentDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-20T16:41:38-0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.10 (Oracle Corporation)"
)
@Component
public class StudentMapperImpl implements StudentMapper {

    @Override
    public StudentDTO toDto(Student entity) {
        if ( entity == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId( entity.getId() );
        studentDTO.setName( entity.getName() );
        studentDTO.setCourses( courseListToCourseDTOList( entity.getCourses() ) );

        return studentDTO;
    }

    @Override
    public List<StudentDTO> toDto(List<Student> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<StudentDTO> list = new ArrayList<StudentDTO>( entityList.size() );
        for ( Student student : entityList ) {
            list.add( toDto( student ) );
        }

        return list;
    }

    @Override
    public List<Student> toEntity(List<StudentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Student> list = new ArrayList<Student>( dtoList.size() );
        for ( StudentDTO studentDTO : dtoList ) {
            list.add( toEntity( studentDTO ) );
        }

        return list;
    }

    @Override
    public Student toEntity(StudentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( dto.getId() );
        student.setName( dto.getName() );
        student.setCourses( courseDTOListToCourseList( dto.getCourses() ) );

        return student;
    }

    protected CourseDTO courseToCourseDTO(Course course) {
        if ( course == null ) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId( course.getId() );
        courseDTO.setName( course.getName() );
        courseDTO.setStudents( toDto( course.getStudents() ) );

        return courseDTO;
    }

    protected List<CourseDTO> courseListToCourseDTOList(List<Course> list) {
        if ( list == null ) {
            return null;
        }

        List<CourseDTO> list1 = new ArrayList<CourseDTO>( list.size() );
        for ( Course course : list ) {
            list1.add( courseToCourseDTO( course ) );
        }

        return list1;
    }

    protected Course courseDTOToCourse(CourseDTO courseDTO) {
        if ( courseDTO == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( courseDTO.getId() );
        course.setName( courseDTO.getName() );
        course.setStudents( toEntity( courseDTO.getStudents() ) );

        return course;
    }

    protected List<Course> courseDTOListToCourseList(List<CourseDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Course> list1 = new ArrayList<Course>( list.size() );
        for ( CourseDTO courseDTO : list ) {
            list1.add( courseDTOToCourse( courseDTO ) );
        }

        return list1;
    }
}
