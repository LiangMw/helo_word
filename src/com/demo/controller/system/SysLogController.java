package com.demo.controller.system;

import com.demo.common.SysConfig;
import com.demo.common.model.User;
import com.demo.common.model.Userlog;
import com.demo.controller.ControllerExt;
import com.demo.util.DwzJson;
import com.jfinal.plugin.activerecord.Page;

public class SysLogController extends ControllerExt {

	
	public void index(){
		User userm = getSessionAttr("loginUser");
		Integer pageNumber = getParaToInt("pageNum")!=null?getParaToInt("pageNum"):1;
		Integer pageSize = getParaToInt("numPerPage")!=null?getParaToInt("numPerPage"):SysConfig.defaultPageSize;
		String keywords = getPara("keywords");
		String funName = getPara("functionname");
		String content = getPara("content");
		String startTime =  getPara("startTime");
		String endTime =  getPara("endTime");
		if(startTime!=null&&startTime!=null){
			if(startTime.length()>0&&endTime.length()>0){
				if(startTime.compareTo(endTime)>0){
					renderJson(DwzJson.error("开始时间大于结束时间，请重新选择"));
					return;
				}
			}
			
		}
		setAttr("keywords", keywords);
		setAttr("content", content);
		setAttr("functionname",funName);
		Page<Userlog> userLogs = Userlog.dao.selectUserLogs(userm.getId(), pageNumber, pageSize, userm.getUsername(),keywords,funName,content,startTime,endTime);
		setAttr("userLogs", userLogs);
		render("UserLog.jsp");
	}

}
