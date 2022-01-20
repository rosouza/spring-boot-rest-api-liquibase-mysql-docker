package com.rosouza.school.service.mapper;

import com.rosouza.school.domain.Student;
import com.rosouza.school.service.dto.StudentDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Student} and its DTO {@link StudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface StudentMapper extends EntityMapper<StudentDTO, Student> {

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        var entity = new Student();
        entity.setId(id);
        return entity;
    }

}
