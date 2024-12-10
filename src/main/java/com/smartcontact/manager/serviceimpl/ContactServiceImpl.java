package com.smartcontact.manager.serviceimpl;

import com.smartcontact.manager.entities.Contact;
import com.smartcontact.manager.entities.User;
import com.smartcontact.manager.helper.ResourceNotFoundException;
import com.smartcontact.manager.repositories.ContactRepository;
import com.smartcontact.manager.service.ContactService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {

        var contactOld = contactRepository.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());



        return contactRepository.save(contactOld);
    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public void delete(int id) {


        contactRepository.deleteById(id);
    }

    @Override
    public Contact getById(int id) {
        return contactRepository.getById(id);
    }

    @Override
    public Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction, User user) {
        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);

        return contactRepository.findByUserAndNameContaining(user, name, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String email, int page, int size, String sortBy, String direction, User user) {

        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);

        return contactRepository.findByUserAndEmailContaining(user, email, pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumber, int page, int size, String sortBy, String direction, User user) {

        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);

        return contactRepository.findByUserAndPhoneNumberContaining(user, phoneNumber, pageable);
    }


    @Override
    public List<Contact> getByUserId(int userId) {
        return contactRepository.findByUserId(userId);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc")? Sort.by(sortBy).descending() :Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page,size,sort);

        return contactRepository.findByUser(user ,pageable);
    }


}
