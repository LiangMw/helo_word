package com.demo.common;

import com.jfinal.kit.PropKit;

public class SysConfig {	
	/*
	 * @分页表格默认每页显示行数
	 */
	public static final int defaultPageSize=PropKit.use("MyConfig.txt").getInt("pageSize",20);
}
