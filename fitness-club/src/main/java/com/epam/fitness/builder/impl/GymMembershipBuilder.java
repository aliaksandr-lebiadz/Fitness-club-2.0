package com.epam.fitness.builder.impl;

import com.epam.fitness.builder.Builder;
import com.epam.fitness.entity.GymMembership;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>Builds an instance of the {@link GymMembership} class.</p>
 *
 * @see Builder
 * @see GymMembership
 */
public class GymMembershipBuilder implements Builder<GymMembership> {

    private static final String ID_COLUMN = "id";
    private static final String MONTHS_AMOUNT_COLUMN = "months_amount";
    private static final String PRICE_COLUMN = "price";

    /**
     * <p>Builds an instance of the {@link GymMembership} class from
     * the supplied {@link ResultSet}.</p>
     *
     * @param resultSet a result set of parameters
     * @return a built gym membership
     */
    @Override
    public GymMembership build(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        int monthsAmount = resultSet.getInt(MONTHS_AMOUNT_COLUMN);
        BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN);
        return new GymMembership(id, monthsAmount, price);
    }
}