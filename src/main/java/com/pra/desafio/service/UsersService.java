package com.pra.desafio.service;


import com.pra.desafio.dao.users.UsersDAO;
import com.pra.desafio.dao.users.jdbc.UsersDAOImp;
import com.pra.desafio.dto.UsersDTO;
import com.pra.desafio.exception.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class UsersService {
    private final UsersDAO usersDAO;



    public UsersService(  UsersDAOImp usersDAO ) {
        this.usersDAO = usersDAO;
    }

    public List<UsersDTO> listAllUsers() {
        return usersDAO.listAllUsers();

    }

     public UsersDTO insertUser(UsersDTO usr) throws UserNotFoundException {
        return usersDAO.insertUser(usr);
    }

    public UsersDTO updateUser(UsersDTO usr) throws UserNotFoundException {
        return usersDAO.updateUser(usr);
    }

    @Transactional
    public void  deleteUser(int id ) throws UserNotFoundException {
        usersDAO.deleteUser(id);
     }

    public UsersDTO getUserByID(int id) throws UserNotFoundException {
        return usersDAO.getUserByID(id);
    }

    public UsersDTO getUserByEMail(String email) {
        return usersDAO.getUserByEMail(email);
    }

}