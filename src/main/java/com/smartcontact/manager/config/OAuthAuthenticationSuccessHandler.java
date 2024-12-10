package com.smartcontact.manager.config;

import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.smartcontact.manager.entities.Providers;
import com.smartcontact.manager.entities.User;
import com.smartcontact.manager.repositories.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Log log = LogFactory.getLog(OAuthAuthenticationSuccessHandler.class.getName());

	@Autowired
	private UserRepository userRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();

		String email = user.getAttribute("email").toString();
		String name = user.getAttribute("name").toString();
		String profile = user.getAttribute("picture").toString();

		User users = new User();
		users.setEmail(email);
		users.setName(name);
		users.setProfile(profile);
		users.setProvider(Providers.GOOGLE);
		users.setProviderId(user.getName());
		users.setPassword("pass@123");
		users.setAbout("i am login with google!!");

		User user2 = userRepository.findByEmail(email).orElse(null);

		if (user2 == null) {
			userRepository.save(users);
			log.info("user saved" + email);
		}

		new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");

	}

}
