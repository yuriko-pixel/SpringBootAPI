package com.basiauth.basiauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.basiauth.basiauth.repository.WholeUserRepositoryImpl;

@Service
public class LoginUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	WholeUserRepositoryImpl repositoryImpl;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		UserDetails user = repositoryImpl.selectOne(username);
		return user;
	}
}
