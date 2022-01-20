package com.rosouza.school.service;

import com.rosouza.school.domain.Course;
import com.rosouza.school.exception.CourseNotExistException;
import com.rosouza.school.repository.CourseRepository;
import com.rosouza.school.service.dto.CourseDTO;
import com.rosouza.school.service.mapper.CourseMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Optional;

import static com.rosouza.school.CourseTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringJUnitConfig(classes = {CourseService.class, CourseMapperImpl.class})
public class CourseServiceTest {

    @Autowired
    CourseService service;

    @MockBean
    CourseRepository courseRepository;

    @Test
    void should_create_entity_id_if_data_is_valid() throws Exception {
        given(courseRepository.save(any(Course.class))).willReturn(entity());
        CourseDTO actualDTO = service.createCourse(dto(true));

        assertThat(actualDTO).isNotNull();
        assertThat(actualDTO.getId()).isEqualTo(ENTITY_ID);
    }

    @Test
    void should_not_create_entity_with_id_throw_bad_request() {
        //The request does includes an entity ID which causes a "BadRequestException" to be thrown.
        assertThatExceptionOfType(new Exception("Bad Request").getClass()).isThrownBy(() -> service.createCourse(dto(false)));
    }


    @Test
    void should_updated_entity_if_data_is_valid() throws Exception {

        given(courseRepository.findById(any())).willReturn(Optional.of(entity()));
        given(courseRepository.save(any(Course.class))).willReturn(entity());
        CourseDTO actualDTO = service.updateCourse(ENTITY_ID, dto(false));

        assertThat(actualDTO).isNotNull();
        assertThat(actualDTO.getId()).isEqualTo(ENTITY_ID);
    }

    @Test
    void should_not_update_when_id_is_null_throw_bad_request() {
        //The request does not include an entity ID which causes a "BadRequestException" to be thrown.
        assertThatExceptionOfType(new Exception("Bad Request").getClass()).isThrownBy(() -> service.updateCourse(null, dto(true)));
    }

    @Test
    void should_not_updated_when_entity_id_can_not_be_found() {
        Optional<Course> emptyEntity = Optional.empty();
        given(courseRepository.findById(ENTITY_NOT_FOUND_ID)).willReturn(emptyEntity);

        assertThatExceptionOfType(new CourseNotExistException().getClass()).isThrownBy(() -> service.updateCourse(ENTITY_NOT_FOUND_ID, dto(false)));
    }

    @Test
    void should_not_delete_when_id_is_null_throw_bad_request() {
        //The request does not include an entity ID which causes a "BadRequestException" to be thrown.
        assertThatExceptionOfType(new Exception("Bad Request").getClass()).isThrownBy(() -> service.deleteCourse(null));
    }

    @Test
    void should_not_delete_when_entity_id_can_not_be_found() {
        Optional<Course> emptyEntity = Optional.empty();
        given(courseRepository.findById(ENTITY_NOT_FOUND_ID)).willReturn(emptyEntity);

        assertThatExceptionOfType(new CourseNotExistException().getClass()).isThrownBy(() -> service.deleteCourse(ENTITY_NOT_FOUND_ID));
    }

    @Test
    void list_should_be_returned_if_there_is_data_in_DB() {
        var page = new PageImpl<>(List.of(entity()));
        given(courseRepository.findAll(any(Pageable.class))).willReturn(page);

        var entities = courseRepository.findAll(Pageable.unpaged());
        assertThat(entities).isNotNull();
        assertThat(entities.getSize()).isEqualTo(1);

        var entity = entities.iterator().next();
        assertThat(entity).isNotNull();
        assertThat(entity).hasFieldOrPropertyWithValue("id", ENTITY_ID);
        assertThat(entity).hasFieldOrPropertyWithValue("name", NAME);
    }

    @Test
    void should_return_if_entity_id_is_valid() {
        given(courseRepository.findById(ENTITY_ID)).willReturn(Optional.of(entity()));
        CourseDTO actualDto = service.getCourse(ENTITY_ID);
        assertThat(actualDto).isNotNull();
    }

    @Test
    void should_not_return_if_entity_id_is_invalid() {
        Optional<Course> emptyDevice = Optional.empty();
        given(courseRepository.findById(ENTITY_NOT_FOUND_ID)).willReturn(emptyDevice);

        assertThatExceptionOfType(new CourseNotExistException().getClass()).isThrownBy(() -> service.getCourse(ENTITY_NOT_FOUND_ID));
    }

    Course entity() {
        var entity = new Course();
        entity.setId(ENTITY_ID);
        entity.setName(NAME);
        return entity;
    }

    CourseDTO dto(boolean isCreate) {
        var dto = new CourseDTO();
        if (!isCreate) {
            dto.setId(ENTITY_ID);
        }
        dto.setName(NAME);
        return dto;
    }

}
