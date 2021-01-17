package com.basiauth.basiauth.entity;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailsImpl implements UserDetails {

	private LoginUser user;

	public UserDetailsImpl(LoginUser user) {
		this.user = user;
	}

    private Collection<? extends GrantedAuthority> authority;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authority;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    /** アカウントの有効期限チェック
     * true: 有効
     * false:無効
     */
    @Override
    public boolean isAccountNonExpired() {

        //ユーザー有効期限が、現在日付よりも後かどうかをチェック
        if(user.getUserDueDate().after(new Date())) {

            //現在日付よりも後なら有効
            return true;

        } else {

            //現在日付よりも前なら無効
            return false;
        }
    }

    /** アカウントのロックチェック
     * true: 有効
     * false: 無効
     */
    @Override
    public boolean isAccountNonLocked() {
        return user.isUnlock();
    }

    /** パスワードの有効期限チェック
     * true:有効
     * false:無効
     */
    @Override
    public boolean isCredentialsNonExpired() {
    	return true;
//        //パスワード有効期限が、現在日付よりも後かどうかをチェック
//        if(user.getPassUpdateDate().after(new Date())) {
//
//            //現在日付よりも後なら有効
//            return true;
//
//        } else {
//
//            //現在日付よりも前なら無効
//            return false;
//
//        }
    }

    /** アカウントの有効・無効チェック
     * true:有効
     * false:無効
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
