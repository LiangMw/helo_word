package com.demo.controller.music;

import java.util.List;

import com.demo.common.model.MBanner;
import com.demo.common.model.SUser;

public class MusicService {
	
	private static  MBanner banner = new MBanner();
	
	public List<MBanner> getBanners(){
		return banner.find("select * from m_banner where 1= 1");
	} 

}
