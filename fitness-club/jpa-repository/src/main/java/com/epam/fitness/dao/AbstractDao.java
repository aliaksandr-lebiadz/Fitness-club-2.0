package com.epam.fitness.dao;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.entity.Identifiable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * <p>An abstract class with some implemented methods from the
 * basic dao interface and some useful methods for other dao classes.</p>
 *
 * @param <T> a class, which implements {@link Identifiable} interface
 * @see Identifiable
 * @see Dao
 */
public abstract class AbstractDao<T extends Identifiable> implements Dao<T> {

    @PersistenceContext
    private EntityManager entityManager;
    private Class<T> entityClass;

    public AbstractDao(Class<T> entityClass){
        this.entityClass = entityClass;
    }

    /**
     * <p>Finds an entity in the table by id.</p>
     *
     * @param id a id of the entity to find
     * @return an optional of entity when supplied id is present
     * in the table and empty optional otherwise
     */
    @Override
    public Optional<T> findById(int id) {
        T entity = entityManager.find(entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> getAll(){
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<T> root = getRoot(criteriaQuery);
        criteriaQuery.select(root);

        TypedQuery<T> query = getQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void save(T entity){
        entityManager.merge(entity);
    }

    @Override
    public void deleteById(int id){
        T entity = entityManager.find(entityClass, id);
        entityManager.remove(entity);
    }

    protected CriteriaBuilder getCriteriaBuilder(){
        return entityManager.getCriteriaBuilder();
    }

    protected CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder criteriaBuilder){
        return criteriaBuilder.createQuery(entityClass);
    }

    protected Root<T> getRoot(CriteriaQuery<T> criteriaQuery){
        return criteriaQuery.from(entityClass);
    }

    protected TypedQuery<T> getQuery(CriteriaQuery<T> criteriaQuery){
        return entityManager.createQuery(criteriaQuery);
    }

}