package com.epam.fitness.dto.mapper;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.entity.Identifiable;
import com.epam.fitness.exception.EntityMappingException;
import org.modelmapper.ModelMapper;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractDtoMapper<E extends Identifiable, D extends Identifiable>
        implements DtoMapper<E,D> {

    private ModelMapper modelMapper;
    private Dao<E> dao;
    private Class<D> dtoClass;

    public AbstractDtoMapper(ModelMapper modelMapper, Dao<E> dao, Class<D> dtoClass){
        this.modelMapper = modelMapper;
        this.dao = dao;
        this.dtoClass = dtoClass;
    }

    @Override
    public E mapToEntity(D dto) throws EntityMappingException{
        int id = dto.getId();
        Optional<E> optionalEntity = dao.findById(id);
        if(optionalEntity.isPresent()){
            E entity = optionalEntity.get();
            setMutableFields(dto, entity);
            return entity;
        } else{
            throw new EntityMappingException("Cannot map dto to entity!");
        }
    }

    @Override
    public D mapToDto(E entity) {
        return Objects.isNull(entity) ? null : modelMapper.map(entity, dtoClass);
    }

    protected abstract void setMutableFields(D source, E destination)
            throws EntityMappingException;
}
