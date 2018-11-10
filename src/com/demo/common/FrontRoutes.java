package com.demo.common;

import com.demo.controller.login.LoginController;
import com.demo.controller.music.MusicController;
import com.demo.controller.test.TestController;
import com.jfinal.config.Routes;

public class FrontRoutes extends Routes {

	@Override
	public void config() {
		
		add("/login",LoginController.class);
		add("/music",MusicController.class);
		add("/test",TestController.class);
		
	}

}
