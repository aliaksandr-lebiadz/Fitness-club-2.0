package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.SortOrder;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final String EMAIL_PARAMETER = "email";
    private static final String ROLE_PARAMETER = "role";
    private static final String ORDERS_FIELD = "orders";
    private static final String TRAINER_PARAMETER = "trainer";
    private static final String FIRST_NAME_PARAMETER = "firstName";
    private static final String SECOND_NAME_PARAMETER = "secondName";

    private static Random random = new Random();

    public UserDaoImpl() {
        super(User.class);
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

        TypedQuery<User> query = getQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public List<User> getAllClients() {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<User> user = getRoot(criteriaQuery);
        criteriaQuery
                .select(user)
                .where(criteriaBuilder.equal(user.get(ROLE_PARAMETER), UserRole.CLIENT));
        TypedQuery<User> query = getQuery(criteriaQuery);
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
        TypedQuery<User> query = getQuery(criteriaQuery);
        return getOptionalResult(query);
    }

    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<User> user = getRoot(criteriaQuery);
        criteriaQuery
                .select(user)
                .where(criteriaBuilder.and(
                        criteriaBuilder.equal(user.get(EMAIL_PARAMETER), email),
                        criteriaBuilder.equal(user.get("password"), password)
                ));
        TypedQuery<User> query = getQuery(criteriaQuery);
        return getOptionalResult(query);
    }

    @Override
    public List<User> findUsersByParameters(String firstName, String secondName, String email, SortOrder sortOrder) {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<User> user = getRoot(criteriaQuery);
        List<Predicate> predicates = getSearchPredicates(criteriaBuilder, user, firstName, secondName, email);
        criteriaQuery
                .select(user)
                .where(convert(predicates));
        if(Objects.nonNull(sortOrder)) {
            Order order = getOrder(criteriaBuilder, user.get(FIRST_NAME_PARAMETER), sortOrder);
            criteriaQuery.orderBy(order);
        }
        TypedQuery<User> query = getQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Optional<User> getRandomTrainer() {
        CriteriaBuilder criteriaBuilder = getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = getCriteriaQuery(criteriaBuilder);
        Root<User> user = getRoot(criteriaQuery);
        criteriaQuery
                .select(user)
                .where(criteriaBuilder.equal(user.get(ROLE_PARAMETER), UserRole.TRAINER));
        TypedQuery<User> query = getQuery(criteriaQuery);
        List<User> trainers = query.getResultList();
        return getRandomTrainerFromList(trainers);
    }

    private Optional<User> getOptionalResult(TypedQuery<User> query){
        try{
            User user = query.getSingleResult();
            return Optional.of(user);
        } catch (NoResultException ex){
            return Optional.empty();
        }
    }

    private Optional<User> getRandomTrainerFromList(List<User> trainers){
        if(trainers.isEmpty()){
            return Optional.empty();
        }

        int size = trainers.size();
        User trainer = trainers.get(random.nextInt(size));
        return Optional.of(trainer);
    }

    private List<Predicate> getSearchPredicates(CriteriaBuilder criteriaBuilder, Root<User> user,
                                                String firstName, String secondName, String email){
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(firstName)){
            Predicate firstNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(user.get(FIRST_NAME_PARAMETER)), "%" + firstName.toLowerCase() + "%");
            predicates.add(firstNamePredicate);
        }
        if(Objects.nonNull(secondName)){
            Predicate secondNamePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(user.get(SECOND_NAME_PARAMETER)), "%" + secondName.toLowerCase() + "%");
            predicates.add(secondNamePredicate);
        }
        if(Objects.nonNull(email)){
            Predicate emailPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(user.get(EMAIL_PARAMETER)), "%" + email.toLowerCase() + "%");
            predicates.add(emailPredicate);
        }
        return predicates;
    }

    private Order getOrder(CriteriaBuilder criteriaBuilder, Expression<User> expression, SortOrder order){
        if(order == SortOrder.ASCENDING){
            return criteriaBuilder.asc(expression);
        } else{
            return criteriaBuilder.desc(expression);
        }
    }

    private Predicate[] convert(List<Predicate> predicates){
        int size = predicates.size();
        Predicate[] predicateList = new Predicate[size];
        return predicates.toArray(predicateList);
    }
}
