package com.epam.fitness.dto.mapper;

import com.epam.fitness.entity.Identifiable;

import java.util.List;

public interface DtoMapper<E extends Identifiable, D extends Identifiable> {

    E mapToEntity(D dto);
    D mapToDto(E entity);
    List<D> mapToDto(List<E> entities);

}
