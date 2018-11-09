package com.demo.controller.login;

import org.apache.log4j.spi.RendererSupport;

import com.demo.controller.ControllerExt;
import com.jfinal.aop.Clear;

public class LoginController extends ControllerExt {
	
	private LoginService ls = new LoginService();
	
	@Clear
	public void register(){
		
		String tel = getPara("tel");
		String pwd = getPara("pwd");
		if(ls.register(tel, pwd)){
			renderAppSuccess("成功");
		}else{
			renderAppError("失败");
		}
		
	}
	
}
