package com.basiauth.basiauth.repository;


import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.basiauth.basiauth.entity.UserDetailsImpl;

public interface WholeUserRepository {
    public UserDetailsImpl selectOne(String userId);
    public List<GrantedAuthority> getRoleList(String userId);
}
