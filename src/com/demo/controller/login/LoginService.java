package com.demo.controller.login;

import java.util.UUID;

import com.demo.common.model.SUser;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.JsonKit;

import net.sf.cglib.core.GeneratorStrategy;

public class LoginService {
	private static SUser user = new SUser();
	
	public static Boolean register(String tel,String pwd){
		String currenttime = System.currentTimeMillis()+"";
		SUser u = new SUser();
		String salt = HashKit.generateSalt(8);
		u.setTel(tel);
		u.setSalt(salt);
		u.setPassword(myHash(pwd,salt));
		u.setUsername("用户"+currenttime.substring(currenttime.length()-5, currenttime.length()));
		u.setGuid(myGuid());
		return u.save();
	}
	
	public static  String myHash(String input,String salt){
		return HashKit.sha256(input+salt);
	}
	
	public static  String myGuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}

}
