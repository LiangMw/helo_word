package com.demo.controller.system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.eclipse.jetty.server.session.JDBCSessionManager.Session;

import com.demo.common.SysConfig;
import com.demo.common.model.Function;
import com.demo.common.model.Roleuser;
import com.demo.common.model.User;
import com.demo.common.model.Userlog;
import com.demo.controller.ControllerExt;
import com.demo.util.DwzJson;
import com.demo.util.StrOperate;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.kit.HashKit;
import com.jfinal.kit.StrKit;

public class UserController extends ControllerExt {
	String ip = null;
	private Userlog userlog = new Userlog();
	
	public void index(){
		Integer pageNumber=getParaToInt("pageNum")!=null?getParaToInt("pageNum"):1;
		Integer pageSize=getParaToInt("numPerPage")!=null?getParaToInt("numPerPage"):SysConfig.defaultPageSize;
		String keywords=getPara("keywords");
		setAttr("keywords",keywords);		
		setAttr("uinfo", User.me.select4Manage(keywords,pageNumber,pageSize));
		renderJsp("UserManage.jsp");		
	}
	
	public void delete(){
		Integer id=getParaToInt();
		try {
			User.me.deleteById(id);
			renderJson(new DwzJson("删除用户成功!", "", "", "").renderJson());
		} catch (Exception e) {
			renderJson(DwzJson.error("删除用户失败，系统中有该用户的关联信息，不可删除！").renderJson());
			// TODO: Log系统搞进来记录异常信息
		}				
	} 
	
	public void showadd(){
		render("AddEditUser.jsp");
	}
	public void showedit(){
		setAttr("model", User.me.findById(getParaToInt()));
		render("AddEditUser.jsp");
	}
	
	@Clear
	public void login() {
		String username = getPara("username");
		String password = getPara("password");
		Integer functionid =1;
		if (StrKit.isBlank(username) || StrKit.isBlank(password)) {
			setAttr("message", "必须输入用户名、密码");
			render("/login.jsp");
			return;
		} else {
			User user = User.me.validateLogin(username, password);
			//Userm user=Userm.me.getuser(username);
			if (user == null) {
				setAttr("message", "不存在用户或用户名与密码不匹配");
				render("/login.jsp");
				return;
			} else {
				// Session中放入User信息
				setSessionAttr("loginUser", user);
				setSessionAttr("loginUserid", user.getId());
				setAttr("loginUsername", user.getRealname());
				List<Function> lv1 = null;
				if (user.getStr("username").equalsIgnoreCase("admin")) {
					lv1 = Function.dao.selectAllLv1Admin();
				} else {
					Roleuser roleuserm=Roleuser.dao.selectone(user.getId());
					
					if(roleuserm!=null){
						
						lv1 = Function.dao.selectLv1FunctionsByUserid(user.getInt("id"));
					}else{
						
						lv1 = Function.dao.selectLv1Functionnone(functionid);
					}
					
				}

				setAttr("lv1Menu", lv1);
				// 获取用户的权限

				ip = StrOperate.getIpAddr(this.getRequest());
				setSessionAttr("ip", ip);
				userlog.addLog(user.getId(), StrOperate.SYSTEMUSERMANAGE, ip, "登录");
				render("/index.jsp");// 登陆系统
			}
		}
	}
	@Clear
	public void dialogloginjsp(){
		
		render("login_dialog.jsp");
		
	}
	@Clear
	public void dialoglogin() {
		String username = getPara("username");
		String password = getPara("password");
		if (StrKit.isBlank(username) || StrKit.isBlank(password)) {
			setAttr("message", "必须输入用户名、密码");
			renderJson(DwzJson.error("必须输入用户名、密码"));
			return;
		} else {
			User user = User.me.validateLogin(username, password);
			if (user == null) {
				// setAttr("message", "不存在用户或用户名与密码不匹配");
				renderJson(DwzJson.error("不存在用户或用户名与密码不匹配"));
				return;
			} else {
				// Session中放入User信息
				setSessionAttr("loginUser", user);
				setAttr("loginUserid", user.getId());
				setSessionAttr("loginUserid", user.getId());
				renderJson(DwzJson.successAndClose("", "", ""));
			}
		}
	}

	@Clear
	public void logout() {
		userlog.addLog(Integer.parseInt(getSessionAttr("loginUserid").toString()),StrOperate.SYSTEMUSERMANAGE, ip, "退出登录");
		removeSessionAttr("loginUser");
		render("/login.jsp");
	}

	public void addedit(){
		try {
			User user=getModel(User.class, "user");
			Integer id=user.getInt("id");
			if(id==null){
				//新建一个用户
				user.set("createtime", new Date());
				user.set("state",1);
				String genSalt=HashKit.generateSalt(8);
				user.set("salt", genSalt);
				user.set("password",User.myHash("123", genSalt));//默认密码123						
				user.save();
				renderJson(DwzJson.closeCurrentAndRefresh("sys_user"));
				
			}else{
				user.set("updatetime", new Date());
				user.update();	
				renderJson(DwzJson.closeCurrentAndRefresh("sys_user"));
			}	
		} catch (Exception e) {
			renderJson(DwzJson.error("操作失败：用户名重复或其他原因--------"+e.getMessage() +"  "+e));
			//TODO: log系统搞进来记录异常信息
		}			
	}
	/**
	 * 更改密码
	 */
	public void resetpw(){
		String oldPassword=getPara("oldPassword");
		String newPassword=getPara("newPassword");
		//String newPassword=getPara("newPassword");
		User user=getSessionAttr("loginUser");
		try {
			if(user==null)
				throw new Exception("获取用户信息出错");
			String username=user.get("username");
			User validateUser=User.me.validateLogin(username, oldPassword);
			if(validateUser!=null){//老的密码通过了验证
				//设置新密码
				user.set("password", User.myHash(newPassword, user.getStr("salt")));
				user.update();//更新新密码
				renderJson(DwzJson.successAndClose("密码更新成功！", "", ""));
//				removeSessionAttr("loginUser");
//				render("/login.jsp");
			}else{
				throw new Exception("输入的密码不正确");				
			}
			
		} catch (Exception e) {
			renderJson(DwzJson.error("修改密码出错！"+e.getMessage()));
		}
		
		
	}
	public void resetpass(){
		User user=User.me.findById(getParaToInt());
		if(user!=null){
			String genSalt=HashKit.generateSalt(8);
			user.set("salt", genSalt);
			user.set("password",HashKit.sha256("123"+genSalt));
			user.update();
			renderJson(DwzJson.success("用户:"+user.getStr("realname")+"  重置密码成功!").renderJson());
		}		else{
			renderJson(DwzJson.error("重置密码失败"));
		}
	}
	
}
