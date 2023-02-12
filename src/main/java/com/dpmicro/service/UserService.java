package com.dpmicro.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dpmicro.dto.UserDTO;
import com.dpmicro.dto.UserResponseDTO;
import com.dpmicro.model.UserModel;
import com.dpmicro.repo.UserRepo;
import com.dpmicro.security.JwtTokenProvider;
import com.dpmicro.utility.JWTUtility;

@Service
public class UserService {

	@Autowired
	JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	UserRepo userRepo;

	public UserResponseDTO findByNameAndPassword(String userName, String password) throws Exception {

		Optional<UserModel> userModel = userRepo.findByNameAndPassword(userName, password);
		if (!userModel.isPresent()) {
			throw new Exception("User Does not exist");
		}
		String token = String.valueOf(jwtTokenProvider.createToken(JWTUtility.setAuthDetails(userModel.get())));
		userModel.get().setToken(token);
		UserModel userModel2 = userRepo.save(userModel.get());
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setId(userModel2.getId());
		userResponseDTO.setEmail(userModel2.getEmail());
		userResponseDTO.setMobileNo(userModel2.getMobileNo());
		userResponseDTO.setName(userModel2.getName());
		userResponseDTO.setUserType(userModel2.getUserType());
		userResponseDTO.setPassword(userModel2.getPassword());
		userResponseDTO.setToken(token);
		return userResponseDTO;
	}

	public UserResponseDTO signUp(UserDTO userDTO) {
		UserModel userModel = new UserModel();
		userModel.setEmail(userDTO.getEmail());
		userModel.setMobileNo(userDTO.getMobileNo());
		userModel.setName(userDTO.getName());
		userModel.setPassword(userDTO.getPassword());
		
		userModel.setUserType(userDTO.getUserType());
		
//		String token = jwtTokenProvider.createToken(OtuUtility.setAuthDetails(userModel));
//		logger.info("Generated token  :: " + token);

//		userModel.setToken(token);
		
		UserModel userModel2 = userRepo.save(userModel);
		
		//Response data with Token
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setId(userModel2.getId());
		userResponseDTO.setEmail(userModel2.getEmail());
		userResponseDTO.setMobileNo(userModel2.getMobileNo());
		userResponseDTO.setName(userModel2.getName());
		userResponseDTO.setUserType(userModel2.getUserType());
//		String.valueOf(jwtTokenProvider.createToken(OtuUtility.setAuthDetails(userModel2)));
//		userResponseDTO.setToken(
//				String.valueOf(jwtTokenProvider.createToken(OtuUtility.setAuthDetails(userModel2))));

//		userResponseDTO.setToken(
//				String.valueOf(jwtTokenProvider.createToken(OtuUtility.setAuthDetails(userModel)).getData()));
		
		return userResponseDTO;

	}

	public UserResponseDTO findById(Long id) {
		
		Optional<UserModel> userModel = userRepo.findById(id);
		
		UserResponseDTO userResponseDTO = new UserResponseDTO();
		userResponseDTO.setId(userModel.get().getId());
		userResponseDTO.setEmail(userModel.get().getEmail());
		userResponseDTO.setMobileNo(userModel.get().getMobileNo());
		userResponseDTO.setName(userModel.get().getName());
		userResponseDTO.setUserType(userModel.get().getUserType());
		userResponseDTO.setToken(userModel.get().getToken());
		return userResponseDTO;
	}

}
