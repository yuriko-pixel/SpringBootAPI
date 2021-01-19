package com.basiauth.basiauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	private EmployeeServiceImpl serviceEmployee;

	@Autowired
	private LoginUserDetailsServiceImpl serviceUser;

	@GetMapping("/admin")
	public String getAdmin() {
		return "admin";
	}

	@GetMapping("/adminhome")
	public String getAdminHome() {
		return "/admin/adminhome";
	}

	//編集画面へのアクセス。雇用者が全員表示される。
	@GetMapping("/admin/edit/employees")
	public String getEdit() {
		return "/admin/editemployee";
	}

	//雇用者の管理画面。IDごとにアップデート可能。
	@GetMapping("/admin/edit/employees/{id}")
	public void editEmployee(@PathVariable("id") Long id, Employee employee) {
		serviceEmployee.updateEmployeeById(employee, id);
	}

	//雇用者の管理。IDごとに削除可能。

	//雇用者の管理。新たな雇用者の追加

	@GetMapping("/admin/edituser")
	public String getAllUsers(Model model) {
		model.addAttribute("usersList", serviceUser.getAllUsers());
//		model.addAttribute("userEditRequest", new LoginUser());
		return "/admin/edituser";
	}

	@GetMapping("/admin/edituser/{userId}")
	public String getAllUsers(@PathVariable("userId") String userId, Model model) {
		model.addAttribute("userInfo", serviceUser.getLoginUserByUserId(userId));
		System.out.println(serviceUser.getLoginUserByUserId(userId).getPassword());
//		model.addAttribute("userEditRequest", new LoginUser());
		return "/admin/editeachuser";
	}
}
