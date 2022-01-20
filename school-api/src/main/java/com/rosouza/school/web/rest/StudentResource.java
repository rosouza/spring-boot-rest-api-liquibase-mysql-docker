package com.rosouza.school.web.rest;

import com.rosouza.school.service.RegistrationService;
import com.rosouza.school.service.StudentService;
import com.rosouza.school.service.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class StudentResource {

    private final StudentService studentService;
    private final RegistrationService registrationService;

    /**
     * {@code GET  /v1/students} : Retrieve all existing students.
     *
     * @return all existing students
     */
    @GetMapping("/v1/students")
    public ResponseEntity<List<StudentDTO>> findAll() {
        log.debug("REST request to retrieve all existing students");

        var result = studentService.findAll();
        return ResponseEntity.ok(result);
    }

    /**
     * {@code POST  /v1/students} : Create a new student.
     *
     * @param studentDTO the studentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new studentDTO,
     * or with status {@code 400 (Bad Request)} if the student has already an ID.
     */
    @PostMapping("/v1/students")
    public ResponseEntity<StudentDTO> createStudent(
            @Valid @RequestBody StudentDTO studentDTO
    ) {
        log.debug("REST request to save Student : {}", studentDTO);

        if (Objects.nonNull(studentDTO.getId())) {
            return ResponseEntity.badRequest().build();
        }

        var result = studentService.createStudent(studentDTO);
        return ResponseEntity.created(URI.create("/v1/students/" + result.getId())).body(result);
    }

    /**
     * {@code DELETE  /v1/students/:id} : delete the "id" student.
     *
     * @param id the id of the Student to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/v1/students/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Long id
    ) {
        log.debug("REST request to delete a Student: {}", id);
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * {@code GET  /v1/students/:id} : get the "id" student.
     *
     * @param id the id of the student to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)},
     * or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/v1/students/{id}")
    public ResponseEntity<StudentDTO> getStudent(
            @PathVariable Long id
    ) {
        log.debug("REST request to get a Student: {}", id);
        var result = studentService.getStudent(id);
        return ResponseEntity.ok(result);
    }

    /**
     * {@code PUT  /v1/students/:id} : Updates an existing student.
     *
     * @param id         the id of the student to update.
     * @param studentDTO the studentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentDTO,
     * or with status {@code 400 (Bad Request)} if the studentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the studentDTO couldn't be updated.
     */
    @PutMapping("/v1/students/{id}")
    public ResponseEntity<StudentDTO> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentDTO studentDTO
    ) {
        log.debug("REST request to update a Student: {} {}", id, studentDTO);
        var result = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * Retrieves all students without any courses.
     *
     * @return the set of students.
     */
    @GetMapping("/v1/students/no-registrations")
    public ResponseEntity<List<StudentDTO>> findStudentsWithNoRegistrations() {
        log.debug("REST request to filter all courses without any students.");
        var result = studentService.findStudentsWithNoRegistrations();
        return ResponseEntity.ok(result);
    }

    /**
     * Registers a given student into a given course.
     *
     * @param studentId the id of the student to update.
     * @param courseId the id of the student to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated studentDTO,
     * or with status {@code 400 (Bad Request)} if the studentId or courseId is not valid,
     * or with status {@code 500 (Internal Server Error)} if the student couldn't be registered.
     */
    @PostMapping("/v1/students/{studentId}/register/{courseId}")
    public ResponseEntity<StudentDTO> register(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        log.debug("REST request to register the student [{}] into a the course[{}].", studentId, courseId);

        if (Objects.isNull(studentId) || Objects.isNull(courseId)) {
            return ResponseEntity.badRequest().build();
        }

        var result = registrationService.register(studentId, courseId);
        return ResponseEntity.ok(result);
    }

}
