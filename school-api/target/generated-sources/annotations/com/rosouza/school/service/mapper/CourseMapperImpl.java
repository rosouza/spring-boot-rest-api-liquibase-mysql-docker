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
public class CourseMapperImpl implements CourseMapper {

    @Override
    public CourseDTO toDto(Course entity) {
        if ( entity == null ) {
            return null;
        }

        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setId( entity.getId() );
        courseDTO.setName( entity.getName() );
        courseDTO.setStudents( studentListToStudentDTOList( entity.getStudents() ) );

        return courseDTO;
    }

    @Override
    public List<CourseDTO> toDto(List<Course> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CourseDTO> list = new ArrayList<CourseDTO>( entityList.size() );
        for ( Course course : entityList ) {
            list.add( toDto( course ) );
        }

        return list;
    }

    @Override
    public List<Course> toEntity(List<CourseDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Course> list = new ArrayList<Course>( dtoList.size() );
        for ( CourseDTO courseDTO : dtoList ) {
            list.add( toEntity( courseDTO ) );
        }

        return list;
    }

    @Override
    public Course toEntity(CourseDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Course course = new Course();

        course.setId( dto.getId() );
        course.setName( dto.getName() );
        course.setStudents( studentDTOListToStudentList( dto.getStudents() ) );

        return course;
    }

    protected StudentDTO studentToStudentDTO(Student student) {
        if ( student == null ) {
            return null;
        }

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setId( student.getId() );
        studentDTO.setName( student.getName() );
        studentDTO.setCourses( toDto( student.getCourses() ) );

        return studentDTO;
    }

    protected List<StudentDTO> studentListToStudentDTOList(List<Student> list) {
        if ( list == null ) {
            return null;
        }

        List<StudentDTO> list1 = new ArrayList<StudentDTO>( list.size() );
        for ( Student student : list ) {
            list1.add( studentToStudentDTO( student ) );
        }

        return list1;
    }

    protected Student studentDTOToStudent(StudentDTO studentDTO) {
        if ( studentDTO == null ) {
            return null;
        }

        Student student = new Student();

        student.setId( studentDTO.getId() );
        student.setName( studentDTO.getName() );
        student.setCourses( toEntity( studentDTO.getCourses() ) );

        return student;
    }

    protected List<Student> studentDTOListToStudentList(List<StudentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Student> list1 = new ArrayList<Student>( list.size() );
        for ( StudentDTO studentDTO : list ) {
            list1.add( studentDTOToStudent( studentDTO ) );
        }

        return list1;
    }
}
