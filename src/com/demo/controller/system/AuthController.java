package com.demo.controller.system;

import java.util.List;

import com.demo.common.SysConfig;
import com.demo.common.model.Function;
import com.demo.common.model.Role;
import com.demo.common.model.Rolefunction;
import com.demo.common.model.Roleuser;
import com.demo.common.model.User;
import com.demo.controller.ControllerExt;
import com.demo.util.DwzJson;
import com.demo.util.StrOperate;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class AuthController extends ControllerExt {
	public void index(){
		setAttr("rlist", Role.me.selectAll());
		render("AuthManage.jsp");
	}
	//显示角色权限
	public void ShowDetail(){
		Integer roleid=getParaToInt();
		setAttr("roleid", roleid);
		setAttr("role",Role.me.findById(roleid));
		List<Function> lv1=Function.dao.selectLv1FunctionsByRoleid(roleid);
		setAttr("lv1", lv1);
		render("RoleFunction.jsp");
	}
	//显示角色用户
	public void ShowRoleUser(){
		Integer roleid=getParaToInt();
		String keywords=getPara("keywords");
		setAttr("roleid", roleid);
		setAttr("role",Role.me.findById(roleid));
		setAttr("keywords", keywords);
		Integer pageNumber=getParaToInt("pageNum")!=null?getParaToInt("pageNum"):1;
		Integer pageSize=getParaToInt("numPerPage")!=null?getParaToInt("numPerPage"):SysConfig.defaultPageSize;
		Page<User> roleUserPage=User.me.selectByRoleid(roleid,keywords,pageNumber, pageSize);				
		setAttr("page", roleUserPage);		
		render("RoleUser.jsp");
	}
	@Before(Tx.class)
	public void showaddauthuser(){		
		Integer roleid=getParaToInt();
		int a=3;
		String keywords=getPara("keywords");
		setAttr("roleid", roleid);
		setAttr("keywords", keywords);
		Integer pageNumber=getParaToInt("pageNum")!=null?getParaToInt("pageNum"):1;
		Integer pageSize=getParaToInt("numPerPage")!=null?getParaToInt("numPerPage"):SysConfig.defaultPageSize;	
		Page<User> roleUserPage=User.me.selectByRoleid4Add(roleid,keywords,pageNumber, pageSize);				
		setAttr("page", roleUserPage);		
		render("AddRoleUser.jsp");
	}
	
	public void deleteuser(){
		Integer roleid=getParaToInt("roleid");
		Integer userid=getParaToInt("userid");
		if(Roleuser.dao.delete(roleid, userid))
			renderJson(DwzJson.success("", "", "authdetail"));
		else
			renderJson(DwzJson.error("删除失败"));
	}
	
	public void saveauth(){
		String[] functionids=getRequest().getParameterValues("functionid");
		try {
			Rolefunction.me.saveRoleAuth(functionids,getParaToInt("roleid"));			
			renderJson(DwzJson.success());
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(DwzJson.error("保存角色权限出错,"+e.getMessage()));
		}		
	}
	
	public void addroleusers(){
		Integer roleid=getParaToInt();
		Integer[] ids=getParaValuesToInt("ids");
		if(ids!=null){
			for (Integer id : ids) {
				Roleuser ru=new Roleuser();
				ru.set("roleid", roleid);
				ru.set("userid", id);
				ru.save();
			}
		}
		renderJson(DwzJson.successAndClose("", "", "authdetail"));
		
	}
	/**
	 * 跳转到添加角色界面
	 */
	public void showaddrolem(){
		render("AddRolem.jsp");
	}
	/**
	 * 后台权限管理中添加角色组
	 */
	public void addrolem(){
		try {
			Role rm = getModel(Role.class);
			Integer id = getParaToInt();
			String rolename_new =getPara("role.rolename");
			String rolename = rm.getRolename();
			List<Role> rms_new = Role.me.selectByName(rolename_new);
			List<Role> rms = Role.me.selectByName(rolename);
			if(rms.size()>0 || rms_new.size()>0){
				renderJson(DwzJson.error("该角色已存在！"));
			}else{
				if(id==null){
					//添加角色组
					rm.save();
					renderJson(DwzJson.closeCurrentAndRefresh("sys_auth"));
					String content = "添加角色："+rm.getRolename()+"，编号："+rm.getId();
					userlog.addLog(Integer.parseInt(getSessionAttr("loginUserid")+""), StrOperate.BACKMANAGE, getSessionAttr("ip")+"", content);
				}else{
					Role rolem	=Role.me.findById(id);
					rolem.set("rolename", rolename_new);
					rolem.update();
					renderJson(DwzJson.closeCurrentAndRefresh("sys_auth"));
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			renderJson(DwzJson.error("操作失败：" + e.getMessage() + "  " + e));
		}
		
	}
	/**
	 * 编辑角色名称
	 */
	  public void showedit() {
			setAttr("model", Role.me.findById(getParaToInt()));
			render("AddRolem.jsp");
		
		}
	  
	  @Before(Tx.class)
		public void deleterolem(){
			Integer roleid=getParaToInt();
			int userid = getSessionAttr("loginUserid");
			String ip = getSessionAttr("ip");
			try {
				List<Roleuser> ruserms = Roleuser.dao.selectByRoleid(roleid);
				List<Rolefunction> rfuns = Rolefunction.me.selectByRoleid(roleid);
				String strUserms = "";
				String strFuns = "";
				if(ruserms.size()!=0){
					int length = ruserms.size();
					for(int i=0;i<length;i++){
						if(i==(length-1)){
							strUserms+=ruserms.get(i).getStr("username")+"。";
						}else{
							strUserms+=ruserms.get(i).getStr("username")+"，";
						}
					}
				}else{
					strUserms = "无";
				}
				if(rfuns.size()!=0){
					int length = rfuns.size();
					for(int i=0;i<length;i++){
						if(i==(length-1)){
							strFuns+=rfuns.get(i).getStr("functionname")+"。";
						}else{
							strFuns+=rfuns.get(i).getStr("functionname")+"，";
						}
					}
				}else{
					strFuns = "无";
				}
				Roleuser.dao.deleteByRoleid(roleid);
				Rolefunction.me.deleteByRoleid(roleid);
				Role.me.deleteById(roleid);
				//renderJson(DwzJson.refresh("AuthManage"));
				renderJson(new DwzJson("删除成功!", "sys_auth", "", "").renderJson());
				String content = "删除角色，编号："+roleid+"。功能："+strFuns+"人员："+strUserms;
				userlog.addLog(userid,StrOperate.BACKMANAGE, ip, content);
				
			} catch (Exception e) {
				// TODO: handle exception
				renderJson(DwzJson.error("删除角色出错,"+e.getMessage()));
			}
		}
		
}
