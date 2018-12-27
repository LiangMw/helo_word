package com.demo.controller.login;

import java.util.List;
import java.util.UUID;
import com.demo.common.model.SToken;
import com.demo.common.model.SUser;
import com.jfinal.kit.HashKit;


public class LoginService {
	private static SUser user = new SUser();
	
	public static Boolean register(String tel,String pwd){
		List<SUser> ou = user.find("select * from s_user where tel = ?",tel);
		if(ou.size()<1){
			String currenttime = System.currentTimeMillis()+"";
			SUser u = new SUser();
			String salt = HashKit.generateSalt(8);
			u.setTel(tel);
			u.setSalt(salt);
			u.setPassword(myHash(pwd,salt));
			u.setUsername("用户"+currenttime.substring(currenttime.length()-5, currenttime.length()));
			u.setGuid(myGuid());
			return u.save();
		}else{
			return false;
		}
	}
	
	public static SToken login(String tel,String pwd){
		SUser u = user.findFirst("select * from s_user where tel = ?",tel);
		if(u==null)
		return null;
		String ipwd = myHash(pwd,u.getSalt());
		if(ipwd.equals(u.getPassword())){
			SToken token =  SToken.dao.findFirst("select * from s_token where guid=?",u.getGuid());
			if(token!=null){
				token.setGuid(u.getGuid());
				token.setToken(HashKit.generateSalt(10));
				token.update();
			}else{
				token = new SToken();
				token.setGuid(u.getGuid());
				token.setToken(HashKit.generateSalt(10)).save();
			}
			return token;
		}else{
			return null;
		}
	}
	
	public static SUser userInfo(String guid){
		SUser u = user.findFirst("select * from s_user where guid = ?",guid);
		return u.remove("password").remove("salt");
	}
	
	public static  String myHash(String input,String salt){
		return HashKit.sha256(input+salt);
	}
	
	public static  String myGuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}

}
