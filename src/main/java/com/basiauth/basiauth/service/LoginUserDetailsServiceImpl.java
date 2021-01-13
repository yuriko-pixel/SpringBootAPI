package com.basiauth.basiauth.service;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.basiauth.basiauth.entity.UserRequest;
import com.basiauth.basiauth.repository.WholeUserRepositoryImpl;

@Service
	public class LoginUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	WholeUserRepositoryImpl repositoryImpl;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		if(username == null) {
			throw new UsernameNotFoundException(username);
		}
		UserDetails user = repositoryImpl.selectOne(username);
		return user;
	}

	public void createUser(UserRequest userRequest) throws ParseException {
		repositoryImpl.createUser(userRequest);

	}
}
