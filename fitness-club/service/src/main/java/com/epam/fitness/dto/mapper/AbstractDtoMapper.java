package com.epam.fitness.dto.mapper;

import com.epam.fitness.entity.Identifiable;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractDtoMapper<E extends Identifiable, D extends Identifiable>
        implements DtoMapper<E,D> {

    private ModelMapper modelMapper;
    private Class<D> dtoClass;
    private Class<E> entityClass;

    public AbstractDtoMapper(ModelMapper modelMapper, Class<E> entityClass, Class<D> dtoClass){
        this.modelMapper = modelMapper;
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
    }

    @Override
    public D mapToDto(E entity){
        return Objects.nonNull(entity) ? modelMapper.map(entity, dtoClass) : null;
    }

    @Override
    public E mapToEntity(D dto){
        return Objects.nonNull(dto) ? modelMapper.map(dto, entityClass) : null;
    }

    @Override
    public List<D> mapToDto(List<E> entities){
        return entities.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
}
