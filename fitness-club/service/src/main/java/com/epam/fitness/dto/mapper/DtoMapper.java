package com.epam.fitness.dto.mapper;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.exception.EntityMappingException;

public interface DtoMapper<E extends Identifiable, D extends Identifiable> {

    E mapToEntity(D dto) throws EntityMappingException;
    D mapToDto(E entity);

}
