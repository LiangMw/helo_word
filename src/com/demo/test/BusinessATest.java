package com.demo.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;
import com.mysql.jdbc.StringUtils;

public class BusinessATest {

	@Test
	public void test() {
		//执行业务测试  跑实际业务
//		int a=1;
//		int b=2;
		//String a="</script>";
		//从配置文件，数据库中读取
		
		
		
//		assertTrue(a+b==2);
		System.out.println(checkToken("6436a6764304f71cd8446f8e0e45d857","/login/userinfo","R-p-fN1vvR"));
		
	}
	

	public static boolean checkToken(String sign, String cKey,String token) {
		//String uid = c.getPara("uid");
		if (StrKit.isBlank(sign)) {
			return false;
		}
		if (token != null) {
			String newsign;
			newsign = cKey + "?token=" + token;
			System.out.println("----"+cKey + "?token=" + token);
			System.out.println("----"+HashKit.md5(newsign));
			if (sign.equals(HashKit.md5(newsign))) {
				return true;
			}
		}
		return false;
	}
	
	@Test
	public void test2(){
		//跑一堆东西
	}
	
	@Test
	public void test3(){
		test();
		test2();
	}

}
