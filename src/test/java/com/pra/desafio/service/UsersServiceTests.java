package com.pra.desafio.service;

import com.pra.desafio.DesafioApplication;
import com.pra.desafio.dto.UsersDTO;
import com.pra.desafio.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(classes = {DesafioApplication.class})
public class UsersServiceTests {

    @Autowired
    private UsersService usersService;

    @Test
    public void getUserByNonExistentIdTest() {
        // Test that trying to get a user with a non-existent ID throws UserNotFoundException
        int nonExistentUserId = 99999;
        assertThatThrownBy(() -> usersService.getUserByID(nonExistentUserId))
            .isInstanceOf(UserNotFoundException.class);
    }
}