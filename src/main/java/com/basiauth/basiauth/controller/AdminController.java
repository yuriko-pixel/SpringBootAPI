package com.basiauth.basiauth.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.basiauth.basiauth.config.OriginalPasswordEncrypter;
import com.basiauth.basiauth.entity.EditUserRequest;
import com.basiauth.basiauth.entity.Employee;
import com.basiauth.basiauth.entity.LoginUser;
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

	@Autowired
	private OriginalPasswordEncrypter passEncrypter;

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
		List<LoginUser> loginUserList = serviceUser.getAllUsers();

		for (int i = 0; i < loginUserList.size(); i++){
			 LoginUser user = loginUserList.get(i);
				String encoded = passEncrypter.decrypt(user.getPassword());
				user.setPassword(encoded);
		}

		model.addAttribute("usersList", loginUserList);

		return "/admin/edituser";
	}

	@GetMapping("/admin/edituser/{userId}")
	public String getAllUsers(@PathVariable("userId") String userId, Model model) {
		LoginUser user = serviceUser.getLoginUserByUserId(userId);
		String decrypt = passEncrypter.decrypt(user.getPassword());
		user.setPassword(decrypt);
		model.addAttribute("userInfo", user);
		List<String> roleIds = new ArrayList<>();
		roleIds.add("general");
		roleIds.add("admin");
		model.addAttribute("roleIds", roleIds);
		model.addAttribute("userEditRequest", new LoginUser());
		return "/admin/editeachuser";
	}

	@PutMapping("/admin/edituser/{userId}")
	public String updateUser(@Valid @ModelAttribute("userRequest") EditUserRequest userRequest, BindingResult result, Model model) throws Exception {
		model.addAttribute("userEditRequest",userRequest);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
    	Date passUpdateDate = df.parse(userRequest.getPassUpdateDate());
    	Date userDueDate = df.parse(userRequest.getUserDueDate());
    	List<String> errorList = new ArrayList<>();

		if(result.hasErrors()) {
			for(ObjectError error: result.getAllErrors()) {
				 errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError",errorList);
			return "admin/editeachuser";

		} else if(passUpdateDate.before(new Date()) || userDueDate.before(new Date())) {
			errorList.add("pass_update_date及びuser_due_dateに過去日付は入力できません。");
			model.addAttribute("userList",serviceUser.getLoginUserByUserId(userRequest.getUserId()));
			model.addAttribute("userEditRequest",serviceUser.getLoginUserByUserId(userRequest.getUserId()));
			model.addAttribute("validationError",errorList);
			model.addAttribute("userInfo", serviceUser.getLoginUserByUserId(userRequest.getUserId()));

			return "/admin/editeachuser";

		} else if(userRequest.getLoginMissTimes() <0) {
			errorList.add("login_miss_timesには0以上の数字を入力してください。");

			model.addAttribute("userInfo", serviceUser.getLoginUserByUserId(userRequest.getUserId()));

			model.addAttribute("userList",serviceUser.getLoginUserByUserId(userRequest.getUserId()));
			model.addAttribute("validationError",errorList);
			return "/admin/editeachuser";
		}

		serviceUser.updateUserInfo(userRequest);

		return "admin/editeachuser";
	}

	@DeleteMapping("/admin/edituser/{userId}")
	public String deleteUser(@PathVariable("userId") String userId, Model model) {
		LoginUser user = serviceUser.getLoginUserByUserId(userId);
		user.setDeleted_flage(true);
		serviceUser.deleteUserByUserid(userId);

		List<LoginUser> loginUserList = serviceUser.getAllUsers();

		for (int i = 0; i < loginUserList.size(); i++){
			 LoginUser user2 = loginUserList.get(i);
				String encoded = passEncrypter.decrypt(user2.getPassword());
				user2.setPassword(encoded);
		}

		String deleteMessage = userId + " を削除しました。";

		model.addAttribute("deleteMessage", deleteMessage);
		model.addAttribute("usersList", loginUserList);

		return "/admin/edituser";
	}
}
