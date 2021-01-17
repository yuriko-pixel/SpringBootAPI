package com.basiauth.basiauth.config;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;

//ログイン後にユーザIDをログに表示する

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor{
	private static final String USER_ID = "USER_ID";
	private static final String SESSION_ID = "SESSION_ID";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Principal user = request.getUserPrincipal();
		String userId = null;
		if(user!=null) {
			userId = user.getName();
		}

		if(userId != null && userId.equals("") == false) {
			MDC.put(USER_ID,userId);
		} else {
			MDC.put(USER_ID,"");
		}

		HttpSession session = request.getSession();
		if(session != null) {
			MDC.put(SESSION_ID,session.getId());
		}
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		MDC.remove(SESSION_ID);
		MDC.remove(USER_ID);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}
