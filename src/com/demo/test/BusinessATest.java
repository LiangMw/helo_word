package com.demo.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mysql.jdbc.StringUtils;

public class BusinessATest {

	@Test
	public void test() {
		//执行业务测试  跑实际业务
		int a=1;
		int b=2;
		//String a="</script>";
		//从配置文件，数据库中读取
		
		
		
		assertTrue(a+b==2);
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
