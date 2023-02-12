package com.dpmicro.security;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dpmicro.model.AppUserRole;
import com.dpmicro.model.UserModel;
import com.dpmicro.repo.UserRepo;

@Service
@RequiredArgsConstructor
public class MyUserDetails implements UserDetailsService {

	@Autowired
	UserRepo userRepo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final Optional<UserModel> appUserAuth = userRepo.findByName(username);

		if (appUserAuth == null)
			throw new UsernameNotFoundException("Username '" + username + "' not found");

		return org.springframework.security.core.userdetails.User//
				.withUsername(username)//
				.password(appUserAuth.get().getMobileNo())//
				.authorities(AppUserRole.values()[appUserAuth.get().getUserType()])//
				.accountExpired(false)//
				.accountLocked(false)//
				.credentialsExpired(false)//
				.disabled(false)//
				.build();
	}

}