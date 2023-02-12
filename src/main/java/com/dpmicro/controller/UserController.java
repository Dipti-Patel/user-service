package com.dpmicro.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dpmicro.dto.UserDTO;
import com.dpmicro.dto.UserResponseDTO;
import com.dpmicro.model.UserLoginDTO;
import com.dpmicro.repo.UserRepo;
import com.dpmicro.security.JwtTokenProvider;
import com.dpmicro.service.UserService;

@RestController
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "signUp", method = {RequestMethod.POST})
	public UserResponseDTO signUp(@RequestBody UserDTO userDTO){
		
		UserResponseDTO userResponseDTO = userService.signUp(userDTO);
		
		return userResponseDTO;
	}
	
	@RequestMapping(value = "singIn", method = {RequestMethod.POST})
	public UserResponseDTO signIn(@RequestBody UserLoginDTO userLoginDTO) throws Exception{
		String userName = userLoginDTO.getUserName();
		String password = userLoginDTO.getPassword();
		UserResponseDTO userResponseDTO = userService.findByNameAndPassword(userName, password);
		
		return userResponseDTO;
	}
	
	@RequestMapping(value = "api/UserInfoFromToken", method = {RequestMethod.GET})
    @PreAuthorize("hasAnyAuthority('NEW_USER')")
	public UserResponseDTO getUserInfoFromToken(@RequestParam Long id) {
		UserResponseDTO userResponseDTO = userService.findById(id);
		return userResponseDTO;
	}

}
