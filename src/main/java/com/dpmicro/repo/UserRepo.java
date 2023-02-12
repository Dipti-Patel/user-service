package com.dpmicro.repo;

import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dpmicro.model.UserModel;

public interface UserRepo extends JpaRepository<UserModel, Long>{

	Optional<UserModel> findByName(String username);

	Optional<UserModel> findByNameAndPassword(String userName, String password);

}
