package com.epam.fitness.mapper;

import com.epam.fitness.entity.GymMembership;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class GymMembershipMapper implements RowMapper<GymMembership> {

    private static final String ID_COLUMN = "id";
    private static final String MONTHS_AMOUNT_COLUMN = "months_amount";
    private static final String PRICE_COLUMN = "price";

    @Override
    public GymMembership mapRow(ResultSet resultSet, int i) throws SQLException {
        int id = resultSet.getInt(ID_COLUMN);
        int monthsAmount = resultSet.getInt(MONTHS_AMOUNT_COLUMN);
        BigDecimal price = resultSet.getBigDecimal(PRICE_COLUMN);
        return new GymMembership(id, monthsAmount, price);
    }
}
