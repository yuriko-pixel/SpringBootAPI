package com.basiauth.basiauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.basiauth.basiauth.entity.PassUpdateRequest;
import com.basiauth.basiauth.entity.UserDetailsImpl;
import com.basiauth.basiauth.service.LoginUserDetailsServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PasswordChangeController {

    @Autowired
    LoginUserDetailsServiceImpl service;

    /**
     * 画面表示.
     * @throws java.text.ParseException
     */
    @GetMapping("/password/change")
    public String getPasswordChange(Model model,
            @ModelAttribute PassUpdateRequest passwordForm) throws java.text.ParseException {
    	model.addAttribute("passwordForm",new PassUpdateRequest());
        return "password_change";
    }

    /**
     * パスワード変更.
     * @throws java.text.ParseException
     * @throws Exception
     */
    @PostMapping("/password/change")
    public String postPasswordChange(Model model,
            @ModelAttribute PassUpdateRequest form,
            @AuthenticationPrincipal UserDetailsImpl user) throws Exception {
    	model.addAttribute("passwordForm",new PassUpdateRequest());
        service.updatePassword(form,user.getUser().getUserId());

        return "home";
    }
}
