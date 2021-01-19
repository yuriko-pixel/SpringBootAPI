package com.basiauth.basiauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.basiauth.basiauth.entity.Employee;
import com.basiauth.basiauth.service.EmployeeServiceImpl;
import com.basiauth.basiauth.service.LoginUserDetailsServiceImpl;

//管理者が限定で使えるメソッド

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {

	@Autowired
	private LoginUserDetailsServiceImpl serviceUsers;

	@Autowired
	private EmployeeServiceImpl serviceEmployee;

	@GetMapping("/admin")
	public String getAdmin() {
		return "admin";
	}

	//編集画面へのアクセス。雇用者が全員表示される。
	@GetMapping("/admin/edit/employees")
	public String getEdit() {

		return "/admin/editemployee";
	}

	//ユーザの編集画面へのアクセス。ユーザが全員表示される。
//	@GetMapping("/admin/edit/users")
//	public String getUsers(Model model) {
//		List<LoginUser> user = serviceUsers.getAllUsers();
//		model.addAttribute("userList", user);
//		return "/admin/editusers";
//	}

	//雇用者の管理画面。IDごとにアップデート可能。
	@GetMapping("/admin/edit/employees/{id}")
	public void editEmployee(@PathVariable Long id, Employee employee) {
		serviceEmployee.updateEmployeeById(employee, id);
	}

	//雇用者の管理。IDごとに削除可能。

	//雇用者の管理。新たな雇用者の追加

}
