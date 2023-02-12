package com.dpmicro.security;

import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dpmicro.client.AuthenticationClient;
import com.dpmicro.model.UserModel;
import com.dpmicro.repo.UserRepo;

import feign.FeignException;

@Component
public class JwtTokenProvider {

	private final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	
	@Autowired
	private MyUserDetails myUserDetails;

	@Autowired
	AuthenticationClient authenticationClient;
	
	@Autowired
	UserRepo userRepo;

	public String createToken(Map<String, Object> authMap) {
		try {
			String token = authenticationClient.token(authMap);
			return token;
		} catch (FeignException feignException) {
			logger.error("user-service : in method create Token :  feign Exception  :: " + feignException);
			throw feignException;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Authentication getAuthentication(String username) {

		UserDetails userDetails = myUserDetails.loadUserByUsername(username);
		Optional<UserModel> userModel = userRepo.findByName(username);

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String resolveToken(HttpServletRequest req) {
		String bearerToken = req.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public String validateToken(String token) {
		try {
			String validateToken = authenticationClient.validateToken(token);
			
			return validateToken;
		} catch (FeignException feignException) {

			logger.error("user-service : in method create Token :  feign Exception  :: " + feignException);
			throw feignException;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
