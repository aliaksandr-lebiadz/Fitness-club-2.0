package com.epam.fitness.dao;

import com.epam.fitness.dao.api.Dao;
import com.epam.fitness.entity.Identifiable;
import org.apache.logging.log4j.LogManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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

    private SessionFactory sessionFactory;
    private Class<T> entityClass;

    public AbstractDao(SessionFactory sessionFactory, Class<T> entityClass){
        this.sessionFactory = sessionFactory;
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
        Session session = getCurrentSession();
        T entity = session.get(entityClass, id);
        return Optional.ofNullable(entity);
    }

    @Override
    public List<T> getAll(){
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);
        Query<T> query = getCurrentSession().createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public void save(T entity){
        LogManager.getLogger().info("save");
        Session session = getCurrentSession();
        session.saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity){
        Session session = getCurrentSession();
        session.delete(entity);
    }

    protected CriteriaBuilder getCriteriaBuilder(){
        return sessionFactory.getCriteriaBuilder();
    }

    protected CriteriaQuery<T> getCriteriaQuery(CriteriaBuilder criteriaBuilder){
        return criteriaBuilder.createQuery(entityClass);
    }

    protected Root<T> getRoot(CriteriaQuery<T> criteriaQuery){
        return criteriaQuery.from(entityClass);
    }

    protected Query<T> getQuery(CriteriaQuery<T> criteriaQuery){
        Session session = getCurrentSession();
        return session.createQuery(criteriaQuery);
    }

    private Session getCurrentSession(){
        return sessionFactory.getCurrentSession();
    }


}