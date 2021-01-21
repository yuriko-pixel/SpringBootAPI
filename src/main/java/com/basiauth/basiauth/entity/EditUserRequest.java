package com.basiauth.basiauth.entity;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

//参考：https://www.baeldung.com/spring-data-jpa-query-by-date

@Data
public class EditUserRequest implements Serializable{

	private String userId;

	@NotEmpty(message="ユーザ名を入力してください")
	@Size(max=255, message="ユーザ名は255字以内で入力してください")
	private String userName;

	@NotEmpty(message = "パスワードを入力してください")
	@Pattern(regexp="^$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!-/:-@\\\\[-`{-~])[!-~]*",message="使用可能な記号は!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~です")
	private String password;

	@NotEmpty(message="パスワードアップデート期限を入力してください")
	private String passUpdateDate;

	@PositiveOrZero(message="0より大きい数を入力してください")
	private int LoginMissTimes;

	private boolean unlock;

	private boolean enabled;

	private String userDueDate;

	private String roleId;
}
