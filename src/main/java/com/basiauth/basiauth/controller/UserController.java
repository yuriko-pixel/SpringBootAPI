package com.basiauth.basiauth.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//ユーザが全員使えるメソッド。

@Controller
@RequestMapping("/user")
public class UserController {

	//自分のユーザ情報を編集するための画面へ飛ぶ。
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN') or ('ROLE_GENERAL')")
	public String getUserEdit() {
		return "/user/useredit";
	}

	@PutMapping("/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN') or ('ROLE_GENERAL')")
	public void updateUser() {

	}

}
