package com.demo.controller.test;

import com.demo.controller.ControllerExt;
import com.jfinal.aop.Clear;

public class TestController extends ControllerExt {
	
	@Clear
	public void test(){
		renderText("44545");
	}
	

}
