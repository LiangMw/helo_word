package com.demo.controller.music;

import com.demo.controller.ControllerExt;
import com.jfinal.aop.Clear;

public class MusicController extends ControllerExt {
	
	private MusicService ms = new MusicService();
	
	/**
	 * 获取banner图
	 */
	@Clear
	public void getbanners(){
		renderAppJson(ms.getBanners());
	}

}
