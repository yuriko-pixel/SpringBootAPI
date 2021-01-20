package com.basiauth.basiauth.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.basiauth.basiauth.entity.EditUserRequest;
import com.basiauth.basiauth.entity.LoginUser;
import com.basiauth.basiauth.entity.PassUpdateRequest;
import com.basiauth.basiauth.entity.Role;
import com.basiauth.basiauth.entity.UserDetailsImpl;
import com.basiauth.basiauth.entity.UserRequest;

@Repository
public class WholeUserRepositoryImpl implements WholeUserRepository{
    @Autowired
    private LoginUserRepository loginUserRepository;

    @Autowired
    private LoginRoleRepository loginRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    //新規ユーザーの登録
    public void createUser(UserRequest userRequest) throws ParseException {
    	LoginUser user = new LoginUser();
    	user.setUserId(userRequest.getUserId());
    	user.setUserName(userRequest.getUserName());
    	user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    	user.setEnabled(true);
    	user.setLoginMissTimes(0);
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	date = df.parse("2099-12-31 23:59:59");

    	user.setPassUpdateDate(date);

    	user.setRoleId("general");
    	user.setUnlock(true);
    	user.setUserDueDate(date);

    	loginUserRepository.save(user);
    }

    //パスワードと、パスワードアップデート日の更新
    public void updatePassword(PassUpdateRequest passUpdateReqeust,String userId) throws ParseException {
    	String password = passwordEncoder.encode(passUpdateReqeust.getPassword());
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date date = new Date();
    	date = df.parse("2099-12-31 23:59:59");

    	loginUserRepository.updatePassword(userId,password,date);
    }

    //パスワードを間違えた回数をアップデート
    public void updateLoginMissTimes(String userId, int loginMissTimes, boolean unlock) {
    	loginUserRepository.updateLogimMissTimes(userId, loginMissTimes,unlock);
    }

    //すべてのユーザー情報を取得
    public List<LoginUser> getAllUsers() {
    	List<LoginUser> userList = new ArrayList<>();
    	Iterable<LoginUser> iterable = loginUserRepository.findAll();
    	iterable.forEach(userList::add);
    	return userList;
    }

    public LoginUser getLoginUserByUserid(String userId) {
    	LoginUser user = loginUserRepository.findOneByUserId(userId);
    	return user;
    }

    public void updateUserInfo(EditUserRequest editUserRequest) throws ParseException {
//
    	String password = passwordEncoder.encode(editUserRequest.getPassword());
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
//
    	Date passUpdateDate = df.parse(editUserRequest.getPassUpdateDate());
//    	int loginMissTimes = Integer.parseInt(loginMissTimes1);
//
//    	boolean unlock = Boolean.getBoolean(unlock1);
//    	boolean enabled = Boolean.getBoolean(enabled1);
    	Date userDueDate = df.parse(editUserRequest.getUserDueDate());

    	System.out.println("passUpdateDate:"+editUserRequest.getUserId());
    	System.out.println("passUpdateDate:"+editUserRequest.getPassUpdateDate());

    	loginUserRepository.updateuserInfo(
    			editUserRequest.getUserId(), editUserRequest.getUserName(), password,
    			passUpdateDate, editUserRequest.getLoginMissTimes(), editUserRequest.isUnlock(),
    			editUserRequest.isEnabled(), userDueDate, editUserRequest.getRoleId());
    }
}
