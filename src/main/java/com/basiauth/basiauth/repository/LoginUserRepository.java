package com.basiauth.basiauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.basiauth.basiauth.entity.LoginUser;

@Repository
public interface LoginUserRepository extends CrudRepository<LoginUser,Long> {
	public LoginUser findOneByUserId(String userId);
}
