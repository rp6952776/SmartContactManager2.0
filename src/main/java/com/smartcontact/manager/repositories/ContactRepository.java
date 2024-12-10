package com.smartcontact.manager.repositories;

import com.smartcontact.manager.entities.Contact;
import com.smartcontact.manager.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {


    Page<Contact> findByUser(User user, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") int userId);

    Page<Contact> findByUserAndNameContaining(User user, String name, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String email, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining( User user, String phoneNumber,Pageable pageable);


}
