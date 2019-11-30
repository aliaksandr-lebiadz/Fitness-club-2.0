package com.epam.fitness.dao.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.dao.api.GymMembershipDao;
import com.epam.fitness.entity.GymMembership;

import java.sql.Connection;

/**
 * <p>An implementation of the gym membership dao interface to provide
 * an access to the gym membership entity in the MySql database.</p>
 *
 * @see GymMembership
 */
public class GymMembershipDaoImpl extends AbstractDao<GymMembership> implements GymMembershipDao {

    private static final String GYM_MEMBERSHIP_TABLE = "gym_membership";

    public GymMembershipDaoImpl(Connection connection, Builder<GymMembership> builder){
        super(connection, builder);
    }

    @Override
    public void save(GymMembership entity) {}

    @Override
    protected String getTableName(){
        return GYM_MEMBERSHIP_TABLE;
    }
}