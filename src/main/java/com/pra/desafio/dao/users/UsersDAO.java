package com.pra.desafio.dao.users;

import com.pra.desafio.dto.UsersDTO;
import com.pra.desafio.exception.UserNotFoundException;

import java.util.List;

public interface UsersDAO {

    public List<UsersDTO>listAllUsers();

    public UsersDTO insertUser(UsersDTO usr) throws UserNotFoundException;

    public UsersDTO updateUser(UsersDTO usr) throws UserNotFoundException;

    public void  deleteUser(int id ) throws UserNotFoundException;

    public UsersDTO getUserByID(int id) throws UserNotFoundException;

    public UsersDTO getUserByEMail(String email);

}
