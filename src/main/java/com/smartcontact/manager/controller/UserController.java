package com.smartcontact.manager.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontact.manager.entities.User;
import com.smartcontact.manager.helper.Helper;
import com.smartcontact.manager.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final Log log = LogFactory.getLog(UserController.class);

	@Autowired
	private UserService userService;

	

	@GetMapping("/dashboard")
	public String dashboard() {
		return "user/dashboard";
	}

	@GetMapping("/profile")
	public String profile(Model model) {

		return "user/profile";
	}

}
