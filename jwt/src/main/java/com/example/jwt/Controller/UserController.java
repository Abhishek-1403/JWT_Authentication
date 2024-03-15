package com.example.jwt.Controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jwt.Configuration.UserDetailsServiceImpl;
import com.example.jwt.DTO.JwtRequestDTO;
import com.example.jwt.entity.User;
import com.example.jwt.jwtConf.JwtHelper;

import ch.qos.logback.classic.Logger;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@GetMapping("users-list")
	public List<String> getUsersList(){
		List<String> list = List.of("Abhishek","Ankit","Aman","Raman","Akash");
		return list;
	}
	
	@Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthenticationManager manager;


    @Autowired
    private JwtHelper helper;

//    private Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtRequestDTO request) {

//        this.doAuthenticate(request.getUserName(), request.getPassword());


    	String userName = request.getUserName();
    	//find user from db
    	User user = new User("second","Abhishek","1234","Admin"); 
    	
    	if(user.getPassword().equals("1234")) {
    		
    		UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
    		
    		String token = this.helper.generateToken(userDetails);
    		return new ResponseEntity<>(token, HttpStatus.OK);
    	}
    	else {
    		throw new AuthorizationServiceException("invalid credentials");
    	}

//        String response = JwtResponse.builder()
//                .jwtToken(token)
//                .username(userDetails.getUsername()).build();
    }

//    private void doAuthenticate(String email, String password) {
//
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
//        try {
////            manager.authenticate(authentication);
//        	
//
//
//        } catch (BadCredentialsException e) {
//            throw new BadCredentialsException(" Invalid Username or Password  !!");
//        }
//
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
