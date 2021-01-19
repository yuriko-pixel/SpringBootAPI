package com.basiauth.basiauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.basiauth.basiauth.entity.UserDetailsImpl;
import com.basiauth.basiauth.service.LoginUserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

//ログイン失敗時の処理

@Component
@Slf4j
public class BadCredentialsEventListener {

    @Autowired
    private LoginUserDetailsServiceImpl service;

    @EventListener
    public void onBadCredentialsEvent(AuthenticationFailureBadCredentialsEvent event) {

        log.info("BadCredentialsEvent Start");

        // 存在しないユーザ名の場合は無視
        if (event.getException().getClass().equals(UsernameNotFoundException.class)) {
            log.info("ユーザーが存在しません。");
            return;
        }

        // ユーザーIDの取得
        String userId = event.getAuthentication().getName();

        // ユーザー情報の取得
        UserDetailsImpl user = (UserDetailsImpl) service.loadUserByUsername(userId);

        // ログイン失敗回数を1増やす
        int loginMissTime = user.getUser().getLoginMissTimes()+ 1;

        // 失敗回数を更新する
        service.updateLoginMissTimes(userId, loginMissTime);
        log.info("BadCredentialsEvent End");
    }
}