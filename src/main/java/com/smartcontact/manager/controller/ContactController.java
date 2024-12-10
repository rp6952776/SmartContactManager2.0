package com.smartcontact.manager.controller;

import com.smartcontact.manager.entities.Contact;
import com.smartcontact.manager.entities.User;
import com.smartcontact.manager.form.ContactForm;
import com.smartcontact.manager.form.ContactSearchForm;
import com.smartcontact.manager.helper.AppConstance;
import com.smartcontact.manager.helper.Helper;
import com.smartcontact.manager.helper.Message;
import com.smartcontact.manager.helper.MessageType;

import com.smartcontact.manager.service.ContactService;
import com.smartcontact.manager.service.ImageService;
import com.smartcontact.manager.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private static final Log log = LogFactory.getLog(ContactController.class.getName());

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @GetMapping("/add-contact")
    public String showAddContactForm(Model model) {
        ContactForm contactForm = new ContactForm();

        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add-contact"; // Your Thymeleaf template name
    }

    @PostMapping("/add")
    public String do_saveContact(@Valid @ModelAttribute ContactForm contactForm, HttpSession session, BindingResult result, Authentication authentication, Model model) {
        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> log.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }


        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact

        User user = userService.getUserByEmail(username);
        // 2 process the contact picture

        // image process

        // uplod karne ka code
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setWebsiteLink(contactForm.getWebsiteLink());


        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactImage(), filename);
            contact.setPicture(fileURL);
            //contact.setCloudinaryImagePublicId(filename);

        }

        contactService.save(contact);
        Message message = Message.builder().content("Contact Add Successful").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "user/add-contact";
    }

    @GetMapping
    public String getContacts(@RequestParam(value = "page",defaultValue = "0") int page,
                              @RequestParam(value = "size",defaultValue = AppConstance.PAGE_SIZE + "") int size,
                              @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
                              @RequestParam(value = "direction",defaultValue = "ascending") String direction,Authentication authentication, Model model){

        String userName= Helper.getEmailOfLoggedInUser(authentication);
        User user1= userService.getUserByEmail(userName);

        Page<Contact> pageContacts = contactService.getByUser(user1,page,size,sortBy,direction);

        model.addAttribute("pageContacts",pageContacts);
        model.addAttribute("pageSize", AppConstance.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());
        return "user/contacts";
    }

    @GetMapping("/search")
    public String searchContact(@ModelAttribute ContactSearchForm contactSearchForm,
                                @RequestParam(value = "page",defaultValue = "0") int page,
                                @RequestParam(value = "size",defaultValue = AppConstance.PAGE_SIZE + "") int size,
                                @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
                                @RequestParam(value = "direction",defaultValue = "ascending") String direction,
                                Model model,
                                Authentication authentication){

        var user= userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContacts = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")){
            pageContacts = contactService.searchByEmail(contactSearchForm.getValue(),page,size,sortBy,direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContacts = contactService.searchByEmail(contactSearchForm.getValue(),page,size,sortBy,direction,user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {
            pageContacts = contactService.searchByPhoneNumber(contactSearchForm.getValue(),page,size,sortBy,direction,user);
        }



        model.addAttribute("pageContacts",pageContacts);
        model.addAttribute("pageSize", AppConstance.PAGE_SIZE);
        model.addAttribute("contactSearchForm",contactSearchForm);

        return "user/search";
    }

    @RequestMapping("/delete/{contactid}")
    public String deleteContact(@PathVariable("contactid") int id, HttpSession session){

        contactService.delete(id);
        log.info("delete contact id " + id);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts";
    }

    @GetMapping("/view/{id}")
    public String viewContact(@PathVariable("id") int id,
                              Model model){

        var contact = contactService.getById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", id);


        return "user/update-contact";
    }


    @PostMapping("/update/{id}")
    public String updateContact(@PathVariable("id") int id,
                                @Valid @ModelAttribute ContactForm contactForm,
                                BindingResult bindingResult,
                                Model model, HttpSession session){

        if (bindingResult.hasErrors()) {
            return "user/update-contact";
        }

        Contact con = contactService.getById(id);
        con.setId(id);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());


        // process image:

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            log.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            //con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);

        } else {
            log.info("file is empty");
        }

        Contact updateCon = contactService.update(con);


        Message message = Message.builder().content("Contact Update Successfully !!").type(MessageType.green).build();
        session.setAttribute("message", message);

        return "redirect:/user/contacts/view/" + id;
    }

}
