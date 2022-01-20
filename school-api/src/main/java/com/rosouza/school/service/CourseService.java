package com.rosouza.school.service;

import com.rosouza.school.exception.CourseNotExistException;
import com.rosouza.school.repository.CourseRepository;
import com.rosouza.school.service.dto.CourseDTO;
import com.rosouza.school.service.mapper.CourseMapper;
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
public class CourseService {

    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    /**
     * Create a Course.
     *
     * @param courseDTO the entity to create.
     * @return the persisted entity.
     */
    @Transactional
    public CourseDTO createCourse(CourseDTO courseDTO) {

        if (Objects.nonNull(courseDTO.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id must be null");
        }

        var result = courseRepository.save(courseMapper.toEntity(courseDTO));
        return courseMapper.toDto(result);
    }

    /**
     * Deletes a Course.
     *
     * @param id the id of the entity.
     */
    @Transactional
    public void deleteCourse(Long id) {
        var course = courseRepository.findById(id).orElseThrow(CourseNotExistException::new);
        courseRepository.delete(course);
    }

    /**
     * Get one Course by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public CourseDTO getCourse(Long id) {
        var result = courseRepository.findById(id).orElseThrow(CourseNotExistException::new);
        return courseMapper.toDto(result);
    }

    /**
     * Update Course by id.
     *
     * @param id        the id of the entity.
     * @param courseDTO the entity to update.
     * @return the entity.
     */
    @Transactional
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        var course = courseRepository.findById(id).orElseThrow(CourseNotExistException::new);
        course = courseMapper.toEntity(courseDTO);
        var result = courseRepository.save(course);
        return courseMapper.toDto(result);
    }

    /**
     * Retrieves all courses without any students.
     *
     * @return the set of courses.
     */
    public List<CourseDTO> findCoursesWithNoRegistrations() {
        var result = courseRepository.findCourseByStudentsEmpty();
        return courseMapper.toDto(result);
    }

    /**
     * Retrieves all existing courses.
     *
     * @return the set of courses.
     */
    public List<CourseDTO> findAll() {
        var result = courseRepository.findAll();
        return courseMapper.toDto(result);
    }

}
