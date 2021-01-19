package com.basiauth.basiauth.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.basiauth.basiauth.entity.UserRequest;
import com.basiauth.basiauth.service.LoginUserDetailsServiceImpl;

@Controller
@RequestMapping("/register")
public class RegisterController {

	@Autowired
	LoginUserDetailsServiceImpl userService;

	@GetMapping
	public String getRegister(Model model) {
		model.addAttribute("UserRequest",new UserRequest());
		return "register";
	}

	@PostMapping
	public String registerUser(@Validated @ModelAttribute UserRequest userRequest, BindingResult result, Model model) throws ParseException {
		if(result.hasErrors()) {
			List<String> errorList = new ArrayList<>();
			for(ObjectError error: result.getAllErrors()) {
				 errorList.add(error.getDefaultMessage());
			}
			model.addAttribute("validationError",errorList);
			return "register";
		}
		
		model.addAttribute("UserRequest", userRequest);
		userService.createUser(userRequest);

		return "registereesult";
	}
}
