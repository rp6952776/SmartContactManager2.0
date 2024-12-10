package com.smartcontact.manager.controller;

import com.smartcontact.manager.entities.Contact;
import com.smartcontact.manager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/contacts/{id}")
    public Contact getContact(@PathVariable int id){

       return contactService.getById(id);
    }
}
