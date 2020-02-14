package com.epam.fitness.dto.mapper;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.exception.ServiceException;

import java.util.List;

public interface DtoMapper<E extends Identifiable, D extends Identifiable> {

    E mapToEntity(D dto) throws ServiceException;
    D mapToDto(E entity) throws ServiceException;
    List<D> mapToDto(List<E> entities) throws ServiceException;

}
