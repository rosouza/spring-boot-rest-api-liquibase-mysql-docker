package com.rosouza.school.service.mapper;

import com.rosouza.school.domain.Course;
import com.rosouza.school.service.dto.CourseDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        var entity = new Course();
        entity.setId(id);
        return entity;
    }

}
