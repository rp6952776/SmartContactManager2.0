package com.smartcontact.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.smartcontact.manager.entities.User;
import com.smartcontact.manager.helper.Helper;
import com.smartcontact.manager.service.UserService;

@ControllerAdvice
public class RootController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute
	public void addLoggedInUserInformation(Model model, Authentication authentication) {
		
		if(authentication==null)
		{
			return;
		}
		
		String userName = Helper.getEmailOfLoggedInUser(authentication);

		User user = userService.getUserByEmail(userName);

		model.addAttribute("loggedInUser", user);

	}

}
