package com.rosouza.school.web.rest;

import com.rosouza.school.service.CourseService;
import com.rosouza.school.service.dto.CourseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class CourseResource {

    private final CourseService courseService;

    /**
     * {@code GET  /v1/courses} : Retrieve all existing courses.
     *
     * @return all existing courses
     */
    @GetMapping("/v1/courses")
    public ResponseEntity<List<CourseDTO>> findAll() {
        log.debug("REST request to retrieve all existing courses");

        var result = courseService.findAll();
        return ResponseEntity.ok(result);
    }

    /**
     * {@code POST  /v1/courses} : Create a new course.
     *
     * @param courseDTO the courseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new courseDTO,
     * or with status {@code 400 (Bad Request)} if the course has already an ID.
     */
    @PostMapping("/v1/courses")
    public ResponseEntity<CourseDTO> createCourse(
        @Valid @RequestBody CourseDTO courseDTO
    ) {
        log.debug("REST request to save Course : {}", courseDTO);

        if (Objects.nonNull(courseDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        var result = courseService.createCourse(courseDTO);
        return ResponseEntity.created(URI.create("/v1/courses/" + result.getId())).body(result);
    }

    /**
     * {@code DELETE  /v1/courses/:id} : delete the "id" course.
     *
     * @param id        the id of the Course to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/v1/courses/{id}")
    public ResponseEntity<Void> deleteCourse(
        @PathVariable Long id
    ) {
        log.debug("REST request to delete a Course: {}", id);
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code GET  /v1/courses/:id} : get the "id" course.
     *
     * @param id the id of the course to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)},
     * or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v1/courses/{id}")
    public ResponseEntity<CourseDTO> getCourse(
        @PathVariable Long id
    ) {
        log.debug("REST request to get a Course: {}", id);
        var result = courseService.getCourse(id);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /v1/courses/:id} : Updates an existing course.
     *
     * @param id             the id of the course to update.
     * @param courseDTO the courseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated courseDTO,
     * or with status {@code 400 (Bad Request)} if the courseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the courseDTO couldn't be updated.
     */
    @PutMapping("/v1/courses/{id}")
    public ResponseEntity<CourseDTO> updateCourse(
        @PathVariable Long id,
        @Valid @RequestBody CourseDTO courseDTO
    ) {
        log.debug("REST request to update a Course: {} {}", id, courseDTO);
        var result = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code GET  /v1/courses/:id/empty} : retrieves all courses without any students.
     *
     */
    @GetMapping("/v1/courses/no-registrations")
    public ResponseEntity<List<CourseDTO>> findCoursesWithNoRegistrations() {
        log.debug("REST request to filter all courses without any students.");
        var result = courseService.findCoursesWithNoRegistrations();
        return ResponseEntity.ok(result);
    }

}
