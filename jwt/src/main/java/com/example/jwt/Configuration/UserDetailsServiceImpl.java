package com.example.jwt.Configuration;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.jwt.entity.User;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		// fetch user from db and load them.
		
		if(username.equals("Abhishek")) {
			
			User user = new User();
			
			user.setUserName("Abhishek");
			user.setPassword("1234");
			user.setId("firstEntry");
			user.setRole("Admin");
			
			CustomUserDetails customUserDetails = new CustomUserDetails(user);
			return customUserDetails;
			
		}
		else {
			throw new UsernameNotFoundException("User does not exist..");
		}
	
//		return customUserDetails;
	}

}
