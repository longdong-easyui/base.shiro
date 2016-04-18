package com.longdong.web.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.longdong.entity.User;
import com.longdong.service.UserService;


@Controller
public class LoginController {
	@Autowired
	private UserService userService;

    @RequestMapping(value = "/login"    )
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }
    
    
    @RequestMapping("toRegisterPage")
    public String toRegisterPage(){
    	return "register";
    }
    
    @RequestMapping("registerUser")
    public String registerUser(User user,Model model){
    	if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
    		model.addAttribute("error", "用户名,密码不能为空。");
    		return "register";
    	}
    	User u = userService.findByUsername(user.getUsername());
    	if(u==null){
    		
    		user.setCreatedDate(new Timestamp(new Date().getTime()));
 			
 			user.setOrganizationId(1L);
    		user.setLocked(1);
        	userService.createUser(user);
        	return "registerSuccess";
    	}else{
    		 model.addAttribute("error", "该用户名已注册，请更换用户名");
    		return "register";
    	}
    }
}
