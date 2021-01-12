package com.basiauth.basiauth.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;

import com.basiauth.basiauth.entity.LoginUser;
import com.basiauth.basiauth.entity.Role;
import com.basiauth.basiauth.entity.UserDetailsImpl;

@Repository
public class WholeUserRepositoryImpl implements WholeUserRepository{
    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private LoginRoleRepository loginRoleRepository;

    @Override
    public UserDetailsImpl selectOne(String userId) {
       LoginUser user = loginUserRepository.findOneByUserId(userId);
       System.out.println(user.getUserName());
       String roleId = user.getRoleId();
       System.out.println(roleId);
       //userIdからrole_idをゲット
       Role role = loginRoleRepository.findOneByRoleId(roleId);
       String roleName = role.getRoleName();

       List<GrantedAuthority> grantedAuthorityList = getRoleList(roleName);
       UserDetailsImpl userDetails = buildUserDetails(user, grantedAuthorityList);
       System.out.println(userDetails);

       return userDetails;
    }

    //Role名を取得ROLE_ADMINとか
    @Override
    public List<GrantedAuthority> getRoleList(String roleName) {

    	List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        GrantedAuthority authority =
                new SimpleGrantedAuthority(roleName);

        grantedAuthorityList.add(authority);

        return grantedAuthorityList;

    }

    private UserDetailsImpl buildUserDetails(LoginUser user,
                                            List<GrantedAuthority> grantedAuthorityList) {
    	new UserDetailsImpl();
		UserDetailsImpl userImpl = UserDetailsImpl.builder()
                .user(user)
                .authority(grantedAuthorityList)
                .build();

        return userImpl;
    }

}
