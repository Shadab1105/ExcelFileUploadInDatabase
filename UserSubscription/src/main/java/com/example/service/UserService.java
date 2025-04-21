package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.User;
import com.example.repository.UserRepository;

@Service
public class UserService 
{
	@Autowired
	private UserRepository userRepository;

	    private List<User> users = new ArrayList<>();

	    public Optional<User> getuserById(Long id) 
	    {
	    	Optional<User> findById = userRepository.findById(id);
	    
	    	return findById;
	    }

//	    public List<User> findUser(String status) 
//	    {
//	        List<User> findUser = new ArrayList<>();
//	        for (User user : users) 
//	        {
//	            if (user.getIsSubscribe().equalsIgnoreCase(status)) 
//	            {
//	            	findUser.add(user);
//	            }
//	        }
//	        return findUser;
//	    }
    
}