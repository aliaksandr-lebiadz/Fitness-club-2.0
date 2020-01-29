package com.epam.fitness.dto.mapper;

import com.epam.fitness.entity.Identifiable;

public interface DtoMapper<E extends Identifiable, D extends Identifiable> {

    E mapToEntity(D dto);
    D mapToDto(E entity);

}
