package com.basiauth.basiauth.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PassUpdateRequest {

	@NotEmpty(message = "パスワードを入力してください")
	@Pattern(regexp="^$|^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!-/:-@\\\\[-`{-~])[!-~]*",message="使用可能な記号は!\"#$%&'()*+,-./:;<=>?@[]^_`{|}~です")
	@Getter
	@Setter
	private String password;

	@Getter
	@Setter
	private Date passUpdateDate;

	public PassUpdateRequest() throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
    	date = df.parse("2099-12-31 23:59:59");

    	passUpdateDate=date;
	}
}
