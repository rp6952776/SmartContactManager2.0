package com.smartcontact.manager.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {
	
	public static String getEmailOfLoggedInUser(Authentication authentication)
	{
		
		
		if(authentication instanceof OAuth2AuthenticationToken)
		{
			var oauth2authentication = (OAuth2AuthenticationToken)authentication;
			var clientId = oauth2authentication.getAuthorizedClientRegistrationId();
			
			
			if(clientId.equalsIgnoreCase("google"))
			{
				DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
				
				String email = user.getAttribute("email").toString();
				
				
				
				return email;
			}
			
			return "";
			
		}
		else
		{
			
			return authentication.getName();
		}
		
			
	}

}
