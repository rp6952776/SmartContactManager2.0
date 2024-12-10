package com.smartcontact.manager.service;

import com.smartcontact.manager.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User Save(User user);

    Optional<User> getUserByid(int id);

    Optional<User> update(User user);

    void Delete(int id);

    boolean isUserExists(String email);

    List<User> getAllUsers();
    
    User getUserByEmail(String email);
}
