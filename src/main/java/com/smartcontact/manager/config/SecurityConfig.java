package com.smartcontact.manager.config;

import com.smartcontact.manager.serviceimpl.SecurityCustomUserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {

    @Autowired
    private SecurityCustomUserDetailService securityCustomUserDetailService;
    
    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

           httpSecurity.authorizeHttpRequests(authorize->{
               authorize.requestMatchers("/user/**").authenticated();
               authorize.anyRequest().permitAll();
           });



           httpSecurity.formLogin(formLogin->{
               formLogin.loginPage("/login");
               formLogin.loginProcessingUrl("/authenticate");
               formLogin.defaultSuccessUrl("/user/profile");
               formLogin.usernameParameter("email");
           });
           
           httpSecurity.csrf(AbstractHttpConfigurer::disable);

           httpSecurity.oauth2Login(oauth->{
        	   oauth.loginPage("/login");
        	   oauth.successHandler(handler);
        	   
           });

           httpSecurity.logout(logoutForm->{
               logoutForm.logoutUrl("/logout");
               logoutForm.logoutSuccessUrl("/login?logout=true");
           });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();

    }
}
