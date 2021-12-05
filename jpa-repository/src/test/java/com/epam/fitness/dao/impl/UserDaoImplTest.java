package com.epam.fitness.dao.impl;

import com.epam.fitness.dao.api.OrderDao;
import com.epam.fitness.dao.api.UserDao;
import com.epam.fitness.entity.order.Order;
import com.epam.fitness.entity.user.User;
import com.epam.fitness.entity.user.UserRole;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


public class UserDaoImplTest extends AbstractDaoTest {

    private static final User CLIENT = new User(5, "client4@mail.ru", "pass1", UserRole.CLIENT, "Alesya", "Kotova", 5);
    private static final User TRAINER = new User(6, "trainer@mail.ru", "pass", UserRole.TRAINER, null, null, 5);

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Test
    public void findClientsOfTrainer_withProvidedTrainer_trainerClients() {
        //given
        orderDao.save(Order.createBuilder().setClient(CLIENT).setTrainer(TRAINER).build());

        //when
        List<User> actualClients = userDao.findClientsOfTrainer(TRAINER);

        //then
        assertEquals(1, actualClients.size());
        assertEquals(CLIENT.getEmail(), actualClients.get(0).getEmail());
        assertEquals(CLIENT.getDiscount(), actualClients.get(0).getDiscount());
        assertEquals(CLIENT.getFirstName(), actualClients.get(0).getFirstName());
        assertEquals(CLIENT.getSecondName(), actualClients.get(0).getSecondName());
    }

    @Test
    public void findUserByEmail_withNonexistentEmail_emptyOptional() {
        //given

        //when
        Optional<User> actualOptional = userDao.findUserByEmail("test@mail.ru");

        //then
        assertFalse(actualOptional.isPresent());
    }

    @Test
    public void findUserByEmail_withExistentEmail_optionalOfUser() {
        //given

        //when
        Optional<User> actualOptional = userDao.findUserByEmail(CLIENT.getEmail());

        //then
        assertTrue(actualOptional.isPresent());
        User actual = actualOptional.get();
        assertEquals(CLIENT.getEmail(), actual.getEmail());
        assertEquals(CLIENT.getDiscount(), actual.getDiscount());
        assertEquals(CLIENT.getFirstName(), actual.getFirstName());
        assertEquals(CLIENT.getSecondName(), actual.getSecondName());
    }

    @Test
    public void getRandomTrainer_withExistentTrainers_randomTrainer() {
        //given
        List<User> trainers = userDao.getAll().stream().filter(user -> user.getRole() == UserRole.TRAINER).collect(Collectors.toList());

        //when
        User actualTrainer = userDao.getRandomTrainer();

        //given
        assertThat(trainers, hasItem(
                hasProperty("id", is(actualTrainer.getId()))
        ));
    }

}