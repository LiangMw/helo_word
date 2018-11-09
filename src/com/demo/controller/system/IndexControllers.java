package com.demo.controller.system;

import com.demo.controller.ControllerExt;
import com.jfinal.aop.Clear;

public class IndexControllers extends ControllerExt {
	
	@Clear
	public void index(){
		
		render("/login.jsp");
	}
	
	
}
