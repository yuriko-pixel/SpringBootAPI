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

        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        grantedAuthorityList.add(authority);

        return grantedAuthorityList;
    }

    private UserDetailsImpl buildUserDetails(LoginUser user,
                                            List<GrantedAuthority> grantedAuthorityList) {
    	UserDetailsImpl userImpl = new UserDetailsImpl().builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .passUpdateDate(user.getPassUpdateDate())
                .loginMissTimes(user.getLoginMissTimes())
                .unlock(user.isUnlock())
                .enabled(user.isEnabled())
                .userDueDate(user.getUserDueDate())
                .authority(grantedAuthorityList)
                .build();

        return userImpl;
    }

}
