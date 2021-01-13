package com.basiauth.basiauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.basiauth.basiauth.entity.UserDetailsImpl;

import lombok.extern.slf4j.Slf4j;

//ADMIN,GENERAL共通のホーム画面の設定

@Controller
@Slf4j
public class HomeController {

    @GetMapping("/home")
    public String getHome(Model model, @AuthenticationPrincipal UserDetailsImpl user) {

        log.info("HomeController Start");

        // ログインユーザー情報の表示
        log.info(user.toString());

        log.info("HomeController End");

        return "home";
    }


}
