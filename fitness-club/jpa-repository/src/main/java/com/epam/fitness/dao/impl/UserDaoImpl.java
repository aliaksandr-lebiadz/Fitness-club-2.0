package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String EMAIL_PARAMETER = "email";
    private static final String ROLE_PARAMETER = "role";
    private static final String ORDERS_FIELD = "orders";
    private static final String TRAINER_PARAMETER = "trainer";

    private static Random random = new Random();

    @Autowired
    public UserDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    @Override
    public List<User> findClientsOfTrainer(User trainer) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<User> user = getRoot(criteriaQuery);

        Join<User, Order> join = user.join(ORDERS_FIELD);
        criteriaQuery
                .select(user)
                .distinct(true)
                .where(criteriaBuilder.equal(join.get(TRAINER_PARAMETER), trainer));

        Query<User> query = getQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<User> findUserByEmail(String email){
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<User> user = getRoot(criteriaQuery);
        criteriaQuery
                .select(user)
                .where(criteriaBuilder.equal(user.get(EMAIL_PARAMETER), email));
        Query<User> query = getQuery(criteriaQuery);
        return query.uniqueResultOptional();
    }

    @Override
    public User getRandomTrainer() {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<User> user = getRoot(criteriaQuery);
        criteriaQuery
                .select(user)
                .where(criteriaBuilder.equal(user.get(ROLE_PARAMETER), UserRole.TRAINER));
        Query<User> query = getQuery(criteriaQuery);
        List<User> trainers = query.getResultList();
        return trainers.get(random.nextInt(trainers.size()));
    }
}
