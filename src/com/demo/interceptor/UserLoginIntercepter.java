package com.demo.interceptor;

import com.demo.common.model.SToken;
import com.demo.controller.ControllerExt;
import com.demo.util.SysStatus;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.InterceptorManager;
import com.jfinal.aop.Invocation;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;

public class UserLoginIntercepter implements Interceptor{
	
	public static String token = "";

	@Override
	public void intercept(Invocation inv) {
		// TODO Auto-generated method stub
		String guid = inv.getController().getPara("guid");
		if(StrKit.isBlank(token)){
			token = getToken(guid);
		}
		System.out.println( inv.getControllerKey() + "/" + inv.getMethodName() + " ,  " + inv);
		if (token.equals(SysStatus.CODETOKENERROR)) {
			((ControllerExt) inv.getController()).renderAppErrorAndCode("Token错误，请重新登录！", SysStatus.CODETOKENERROR);
			return;
		}
		inv.invoke();
	}
	
	/**
	 * 根据guid查询token
	 * @param guid
	 * @return
	 */
	private static String getToken(String guid){
		SToken t = SToken.dao.findFirst("select t.id, t.token from s_token t left join s_user u on t.guid = u.guid where u.guid=?",guid);
		if(t !=null){
			return StrKit.isBlank(t.getToken())?SysStatus.CODETOKENERROR:t.getToken();
		}else{
			return SysStatus.CODETOKENERROR;
		}
	} 

}
