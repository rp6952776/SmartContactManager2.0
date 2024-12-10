package com.smartcontact.manager.service;


import com.smartcontact.manager.entities.Contact;
import com.smartcontact.manager.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();


    void delete(int id);

    Contact getById(int id);

    Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction,User user);

    Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String direction, User user);

    Page<Contact> searchByPhoneNumber(String phoneNumber, int page, int size, String sortBy, String direction,User user);

    List<Contact> getByUserId(int userId);

    Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);


}
