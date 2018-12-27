package com.demo.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchUtils {
	
	/**
	 * 匹配成功返回 1
	 * @param phone
	 * @return
	 */
	public static String isPhone(String phone) {
	    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	    if (phone.length() != 11) {
	        return "手机号应为11位数";
	    } else {
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(phone);
	        boolean isMatch = m.matches();
	        if (!isMatch) {
	            return "请填入正确的手机号";
	        }
	        return "1";
	    }
	}
	
	/**
	 * 匹配成功返回 1
	 * @param phone
	 * @return
	 */
	public static String isPwdSequarty(String pwd) {
	    if (pwd.length() < 6) {
	        return "请输入6位以上密码";
	    } else {
	        return "1";
	    }
	}
	
}
