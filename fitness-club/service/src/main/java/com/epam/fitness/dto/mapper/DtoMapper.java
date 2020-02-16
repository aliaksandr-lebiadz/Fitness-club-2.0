package com.epam.fitness.dto.mapper;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.exception.DtoMappingException;

import java.util.List;

public interface DtoMapper<E extends Identifiable, D extends Identifiable> {

    E mapToEntity(D dto) throws DtoMappingException;
    D mapToDto(E entity) throws DtoMappingException;
    List<D> mapToDto(List<E> entities) throws DtoMappingException;

}
