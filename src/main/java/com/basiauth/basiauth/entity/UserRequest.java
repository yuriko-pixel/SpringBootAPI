package com.basiauth.basiauth.entity;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequest implements Serializable{

	@NotEmpty(message = "ユーザIDを入力してください")
	@Size(max=255, message="ユーザ名は255字以内で入力してください")
	private String userId;

	@NotEmpty(message="ユーザ名を入力してください")
	@Size(max=255, message="ユーザ名は255字以内で入力してください")
	private String userName;

	@NotEmpty(message = "パスワードを入力してください")
	@Pattern(regexp="^$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!-/:-@\\\\[-`{-~])[!-~]*",message="使用可能な記号は!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~です")
	private String password;

}
