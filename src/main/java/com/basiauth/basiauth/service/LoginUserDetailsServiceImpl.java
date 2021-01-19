package com.basiauth.basiauth.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.basiauth.basiauth.entity.LoginUser;
import com.basiauth.basiauth.entity.PassUpdateRequest;
import com.basiauth.basiauth.entity.UserRequest;
import com.basiauth.basiauth.repository.WholeUserRepositoryImpl;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
	public class LoginUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	WholeUserRepositoryImpl repositoryImpl;

	private static final int LOGIN_MISS_TIMES = 3;

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

	public void updatePassword(PassUpdateRequest request, String userId) throws ParseException {
		repositoryImpl.updatePassword(request, userId);
	}

	public void updateLoginMissTimes(String userId, int loginMissTimes) {
		boolean unlock = true;

		if(loginMissTimes >= LOGIN_MISS_TIMES) {
			unlock = false;
			log.info(userId+"をロックします");
		}

		repositoryImpl.updateLoginMissTimes(userId, loginMissTimes, unlock);
	}

	public List<LoginUser> getAllUsers() {
		return repositoryImpl.getAllUsers();
	}

	public LoginUser getLoginUserByUserId(String userId) {
		return repositoryImpl.getLoginUserByUserid(userId);
	}
}
