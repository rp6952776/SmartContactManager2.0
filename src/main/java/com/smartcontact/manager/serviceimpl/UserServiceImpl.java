package com.smartcontact.manager.serviceimpl;

import com.smartcontact.manager.entities.User;
import com.smartcontact.manager.helper.ResourceNotFoundException;
import com.smartcontact.manager.repositories.UserRepository;
import com.smartcontact.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User Save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByid(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> update(User user) {

        User user1 = userRepository.findById(user.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        user1.setUserId(user.getUserId());
        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        user1.setPassword(user.getPassword());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setAbout(user.getAbout());
        user1.setProfile(user.getProfile());
        user1.setProvider(user.getProvider());
        user1.setProviderId(user.getProviderId());

       User save = userRepository.save(user1);

       return Optional.ofNullable(save);
    }

    @Override
    public void Delete(int id) {

        User user1 = userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found"));
        userRepository.deleteById(id);
    }

    @Override
    public boolean isUserExists(String email) {

        User user = userRepository.findByEmail(email).orElse(null);

        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

	@Override
	public User getUserByEmail(String email) {
		
		return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(null));
	}
}
