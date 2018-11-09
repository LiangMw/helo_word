package com.demo.util;

import java.util.HashMap;

public class SysStatus {

	/**
	 * 成功
	 */
	public static final String CODESUCCESS = "0";
	/**
	 * 默认错误编码:系统繁忙
	 */
	public static final String CODECOMMONERROR = "-1";
	/**
	 * token 错误
	 */
	public static final String CODETOKENERROR = "10001";
	/**
	 * 登录认证失败
	 */
	public static final String CODELOGINERROR = "10002";
	/**
	 * 修改密码失败
	 */
	public static final String CODECHANGEPWERROR = "10003";
	/**
	 * 登录用户错误
	 */
	public static final String CODEUSERERROR = "10004";
	/**
	 * 登录密码错误
	 */
	public static final String CODEPASSWORDERROR = "10005";
	/**
	 * 用户被锁定
	 */
	public static final String CODEUSERLOCKED = "10006";
	/**
	 * 未获取登录设备的极光设备ID
	 */
	public static final String CODEREGIDLOST = "10007";
	/**
	 * 登录设备未授权
	 */
	public static final String CODEDEVICEUNAUTHED = "10008";
	/**
	 * 登录设备非法
	 */
	public static final String CODEDEVICEILLEGAL = "10009";
	/**
	 * 发送验证码失败
	 */
	public static final String CODESMSERROR = "10010";
	/**
	 * 权限出错
	 */
	public static final String CODEPERMISSIONDENIED = "10011";

	/**
	 * APP用户中信息不完整(没有身份证信息)
	 */
	public static final String CODEGZAPPUSERERROR = "20001";

	/**
	 * APP用户错误或TOKEN失效
	 */
	public static final String CODEGZAPPTOKENNULL = "20002";
	/**
	 * 工资库查询需要输入密码
	 */
	public static final String CODEGZPWNULL = "20003";
	/**
	 * 工资库中没有相对应的用户
	 */
	public static final String CODEGZUSERNULL = "20004";
	/**
	 * 工资库查询密码错误
	 */
	public static final String CODEGZPWERROR = "20005";
	/**
	 * 工资库用户未激活
	 */
	public static final String CODEGZUSERUNACTIVATED = "20006";
	/**
	 * 加解密出错
	 */
	public static final String CODEDESERROR = "20007";
	/**
	 * 传参错误
	 */
	public static final String CODEPARERROR = "20008";
	/**
	 * 工资库查询需要输入年月
	 */
	public static final String CODEGZYMNULL = "20009";
	/**
	 * 激活信息与APP库不一致
	 */
	public static final String CODEGZAPPINFOERROR = "20010";
	// /**
	// * 工资库密码错误
	// */
	// public static final String CODEGZPWERROR = "20011";

	public static HashMap<String, String> map = new HashMap<String, String>();

	public static HashMap<String, String> initStatusMap() {

		map.put(SysStatus.CODECOMMONERROR, "系统错误");
		map.put(SysStatus.CODESUCCESS, "成功");
		return map;
	}

}
