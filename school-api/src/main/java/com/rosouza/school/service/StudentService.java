package com.rosouza.school.service;

import com.rosouza.school.exception.StudentNotExistException;
import com.rosouza.school.repository.StudentRepository;
import com.rosouza.school.service.dto.StudentDTO;
import com.rosouza.school.service.mapper.StudentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudentService {


    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final CourseService courseService;

    /**
     * Create a Student.
     *
     * @param studentDTO the entity to create.
     * @return the persisted entity.
     */
    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {

        if (Objects.nonNull(studentDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be null");
        }

        var result = studentRepository.save(studentMapper.toEntity(studentDTO));
        return studentMapper.toDto(result);
    }

    /**
     * Deletes a Student.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void deleteStudent(Long id) {
        var student = studentRepository.findById(id).orElseThrow(StudentNotExistException::new);
        studentRepository.delete(student);
    }

    /**
     * Get one Student by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public StudentDTO getStudent(Long id) {
        var result = studentRepository.findById(id).orElseThrow(StudentNotExistException::new);
        return studentMapper.toDto(result);
    }

    /**
     * Update Student by id.
     *
     * @param id         the id of the entity.
     * @param studentDTO the entity to update.
     * @return the entity.
     */
    @Transactional
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        var student = studentRepository.findById(id).orElseThrow(StudentNotExistException::new);
        student = studentMapper.toEntity(studentDTO);
        var result = studentRepository.save(student);
        return studentMapper.toDto(result);
    }

    /**
     * Retrieves all students without any courses.
     *
     * @return the set of students.
     */
    public List<StudentDTO> findStudentsWithNoRegistrations() {
        var result = studentRepository.findStudentByCoursesEmpty();
        return studentMapper.toDto(result);
    }

    /**
     * Retrieves all existing students.
     *
     * @return the set of students.
     */
    public List<StudentDTO> findAll() {
        var result = studentRepository.findAll();
        return studentMapper.toDto(result);
    }

}
