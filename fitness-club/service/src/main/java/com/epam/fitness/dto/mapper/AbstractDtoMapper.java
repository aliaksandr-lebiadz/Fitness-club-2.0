package com.epam.fitness.dto.mapper;

import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.exception.DtoMappingException;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public D mapToDto(E entity) throws DtoMappingException {
        if(Objects.isNull(entity)){
            throw new DtoMappingException("Supplied entity is null and cannot be mapped!");
        }
        return modelMapper.map(entity, dtoClass);
    }

    @Override
    public E mapToEntity(D dto) throws DtoMappingException{
        if(Objects.isNull(dto)){
            throw new DtoMappingException("Supplied dto is null and cannot be mapped!");
        }
        return modelMapper.map(dto, entityClass);
    }

    @Override
    public List<D> mapToDto(List<E> entities) throws DtoMappingException{
        List<D> dtoList = new ArrayList<>();
        for(E entity : entities){
            D dto = mapToDto(entity);
            dtoList.add(dto);
        }
        return dtoList;
    }
}
