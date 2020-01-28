package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.AbstractDao;
import com.epam.fitness.entity.GymMembership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>An implementation of the gym membership dao interface to provide
 * an access to the gym membership entity in the MySql and PostgreSql database.</p>
 *
 * @see GymMembership
 */
@Repository
public class PostgreSqlGymMembershipDao extends AbstractDao<GymMembership> {

    private static final String GYM_MEMBERSHIP_TABLE = "gym_membership";

    @Autowired
    public PostgreSqlGymMembershipDao(JdbcTemplate jdbcTemplate, RowMapper<GymMembership> rowMapper){
        super(jdbcTemplate, rowMapper);
    }

    @Override
    public void save(GymMembership gymMembership) {
        //save operation is unsupported for GymMembership entity at this moment
    }

    @Override
    protected String getTableName(){
        return GYM_MEMBERSHIP_TABLE;
    }

    @Override
    protected String getSaveQuery() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Object[] getFields(GymMembership gymMembership) {
        throw new UnsupportedOperationException();
    }
}