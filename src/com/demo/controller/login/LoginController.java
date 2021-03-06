package com.demo.controller.login;

import com.demo.common.model.SToken;
import com.demo.common.model.SUser;
import com.demo.controller.ControllerExt;
import com.demo.interceptor.AutherIntercepter;
import com.demo.util.MatchUtils;
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.kit.StrKit;

public class LoginController extends ControllerExt {

	private LoginService ls = new LoginService();

	@Clear
	public void register() {
		String tel = getPara("tel");
		String pwd = getPara("pwd");
		if (StrKit.isBlank(tel) || StrKit.isBlank(pwd)) {
			renderAppError("账号或密码不能为空");
		} else {
			String telmatch = MatchUtils.isPhone(tel);
			String pwdmatch = MatchUtils.isPwdSequarty(pwd);
			if ("1".equals(telmatch)) {
				if ("1".equals(pwdmatch)) {
					if (ls.register(tel, pwd)) {
						renderAppSuccess("成功");
					} else {
						renderAppError("失败");
					}
				} else {
					renderAppError(pwdmatch);
				}
			} else {
				renderAppError(telmatch);
			}
		}
	}

	@Clear
	public void login() {
		String tel = getPara("tel");
		String pwd = getPara("pwd");
		if (StrKit.isBlank(tel) || StrKit.isBlank(pwd)) {
			renderAppError("账号或密码不能为空");
		} else {
			SToken token = ls.login(tel, pwd);
			if (token == null) {
				renderAppError("账号或密码错误");
			} else {
				renderAppJson(token);
			}
		}
	}
	
	@Clear
	public void logout() {
		String guid = getPara("guid");
		if (StrKit.isBlank(guid)) {
			renderAppError("参数错误");
		} else {
			Boolean token = ls.logout(guid);
			if (token) {
				renderAppSuccess("退出成功");
			} else {
				renderAppError("退出出错");
			}
		}
	}

	@Clear
	@Before(AutherIntercepter.class)
	public void userinfo() {
		String guid = getPara("guid");
		SUser u = ls.userInfo(guid);
		if (u == null) {
			renderAppError("无此用户");
		} else {
			renderAppJson(u);
		}
	}
	
}
