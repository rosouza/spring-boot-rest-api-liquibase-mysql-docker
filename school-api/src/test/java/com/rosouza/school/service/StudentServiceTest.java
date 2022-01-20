package com.rosouza.school.service;

import com.rosouza.school.domain.Student;
import com.rosouza.school.exception.StudentNotExistException;
import com.rosouza.school.repository.StudentRepository;
import com.rosouza.school.service.dto.StudentDTO;
import com.rosouza.school.service.mapper.StudentMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static com.rosouza.school.StudentTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringJUnitConfig(classes = {StudentService.class, StudentMapperImpl.class})
public class StudentServiceTest {

    @Autowired
    StudentService service;

    @MockBean
    StudentRepository studentRepository;

    @MockBean
    CourseService courseService;

    @Test
    void should_create_entity_id_if_data_is_valid() throws Exception {
        given(studentRepository.save(any(Student.class))).willReturn(entity());
        StudentDTO actualDTO = service.createStudent(dto(true));

        assertThat(actualDTO).isNotNull();
        assertThat(actualDTO.getId()).isEqualTo(ENTITY_ID);
    }

    @Test
    void should_not_create_entity_with_id_throw_bad_request() {
        //The request does includes an entity ID which causes a "BadRequestException" to be thrown.
        assertThatExceptionOfType(new Exception("Bad Request").getClass()).isThrownBy(() -> service.createStudent(dto(false)));
    }


    @Test
    void should_updated_entity_if_data_is_valid() throws Exception {

        given(studentRepository.findById(any())).willReturn(Optional.of(entity()));
        given(studentRepository.save(any(Student.class))).willReturn(entity());
        StudentDTO actualDTO = service.updateStudent(ENTITY_ID, dto(false));

        assertThat(actualDTO).isNotNull();
        assertThat(actualDTO.getId()).isEqualTo(ENTITY_ID);
    }

    @Test
    void should_not_update_when_id_is_null_throw_bad_request() {
        //The request does not include an entity ID which causes a "BadRequestException" to be thrown.
        assertThatExceptionOfType(new Exception("Bad Request").getClass()).isThrownBy(() -> service.updateStudent(null, dto(true)));
    }

    @Test
    void should_not_updated_when_entity_id_can_not_be_found() {
        Optional<Student> emptyEntity = Optional.empty();
        given(studentRepository.findById(ENTITY_NOT_FOUND_ID)).willReturn(emptyEntity);

        assertThatExceptionOfType(new StudentNotExistException().getClass()).isThrownBy(() -> service.updateStudent(ENTITY_NOT_FOUND_ID, dto(false)));
    }

    @Test
    void should_not_delete_when_id_is_null_throw_bad_request() {
        //The request does not include an entity ID which causes a "BadRequestException" to be thrown.
        assertThatExceptionOfType(new Exception("Bad Request").getClass()).isThrownBy(() -> service.deleteStudent(null));
    }

    @Test
    void should_not_delete_when_entity_id_can_not_be_found() {
        Optional<Student> emptyEntity = Optional.empty();
        given(studentRepository.findById(ENTITY_NOT_FOUND_ID)).willReturn(emptyEntity);

        assertThatExceptionOfType(new StudentNotExistException().getClass()).isThrownBy(() -> service.deleteStudent(ENTITY_NOT_FOUND_ID));
    }

    @Test
    void list_should_be_returned_if_there_is_data_in_DB() {
        var page = new PageImpl<>(List.of(entity()));
        given(studentRepository.findAll(any(Pageable.class))).willReturn(page);

        var entities = studentRepository.findAll(Pageable.unpaged());
        assertThat(entities).isNotNull();
        assertThat(entities.getSize()).isEqualTo(1);

        var entity = entities.iterator().next();
        assertThat(entity).isNotNull();
        assertThat(entity).hasFieldOrPropertyWithValue("id", ENTITY_ID);
        assertThat(entity).hasFieldOrPropertyWithValue("name", NAME);
    }

    @Test
    void should_return_if_entity_id_is_valid() {
        given(studentRepository.findById(ENTITY_ID)).willReturn(Optional.of(entity()));
        StudentDTO actualDto = service.getStudent(ENTITY_ID);
        assertThat(actualDto).isNotNull();
    }

    @Test
    void should_not_return_if_entity_id_is_invalid() {
        Optional<Student> emptyDevice = Optional.empty();
        given(studentRepository.findById(ENTITY_NOT_FOUND_ID)).willReturn(emptyDevice);

        assertThatExceptionOfType(new StudentNotExistException().getClass()).isThrownBy(() -> service.getStudent(ENTITY_NOT_FOUND_ID));
    }

    Student entity() {
        var entity = new Student();
        entity.setId(ENTITY_ID);
        entity.setName(NAME);
        return entity;
    }

    StudentDTO dto(boolean isCreate) {
        var dto = new StudentDTO();
        if (!isCreate) {
            dto.setId(ENTITY_ID);
        }
        dto.setName(NAME);
        return dto;
    }

}
