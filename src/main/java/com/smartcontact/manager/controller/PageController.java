package com.smartcontact.manager.controller;

import com.smartcontact.manager.entities.User;

import com.smartcontact.manager.helper.Message;
import com.smartcontact.manager.helper.MessageType;
import com.smartcontact.manager.serviceimpl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PageController {

	private static final Logger log = Logger.getLogger(PageController.class.getName());
	
	@Autowired
	private UserServiceImpl userServiceImpl;

	@GetMapping("/service")
	public String showService()
	{
		log.info("index run");
		return "service";
	}
	
	@GetMapping("/index")
	public String showIndex()
	{
		return "about";
	}

	@GetMapping("/home")
	public String showHome()
	{
		return "home";
	}

	
	@GetMapping("/login")
	public String logins()
	{
		log.info("hiii");
		return "login";
	}

	@GetMapping("/contact")
	public String contact()
	{
		log.info("contact");
		return "contact";
	}

	@GetMapping("/register")
	public String registers(Model model)
	{

		return "register";
	}

	@PostMapping(value = "/do-register")
	public String processRegister(@Valid @ModelAttribute User user, BindingResult result,HttpSession session,Model model)
	{

		if(result.hasErrors())
		{
			return "register";
		}
		
		user.setProfile("https://upload.wikimedia.org/wikipedia/commons/2/2c/Default_pfp.svg");
		userServiceImpl.Save(user);
		

		  Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
		  session.setAttribute("message",message);

		return "redirect:/register";
	}
}