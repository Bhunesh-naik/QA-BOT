package com.naik.QA_BOT.service;


import com.naik.QA_BOT.entity.User;
import com.naik.QA_BOT.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    // Register logic
    public String register(User user){

        User existingUser = userRepository.findByUsername(user.getUsername());

        if(existingUser != null){
            return "Username already exists";
        }

        userRepository.save(user);
        return "Registration Successful";
    }

    // Login logic
    public String login(User loginRequest){

        User user = userRepository.findByUsername(loginRequest.getUsername());

        if(user != null && user.getPassword().equals(loginRequest.getPassword())){
            return "Login Successful";
        }

        return "Invalid Username or Password";
    }
}
