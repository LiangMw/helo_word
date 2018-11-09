package com.demo.common.model;

import java.util.List;

import com.demo.common.model.base.BaseRolefunction;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Rolefunction extends BaseRolefunction<Rolefunction> {
	public static final Rolefunction me = new Rolefunction().dao();
	
	@Before(Tx.class)
	public void saveRoleAuth(String[] functionIds,Integer roleid){
		Db.update("delete from t_rolefunction where roleid=?",roleid);//删除老的角色权限
		if(functionIds!=null){
			for (String fid : functionIds) {
				Rolefunction tmp=new Rolefunction();
				tmp.set("roleid", roleid);
				tmp.set("functionid", fid);
				tmp.save();
			}
		}		
	}
	
	/**
	 * 根据roleid删除表中数据
	 * @param roleid
	 * @return
	 */
	public Integer deleteByRoleid(Integer roleid){
		return Db.update("delete from t_rolefunction where roleid=?", roleid);
	}
	
	/**
	 * 根据roleid查询表中数据
	 * @param roleid
	 * @return
	 */
	public List<Rolefunction> selectByRoleid(Integer roleid){
		return find("select trfun.*,functionname from t_rolefunction trfun inner join t_function tfun on trfun.functionid = tfun.id where trfun.roleid = ?", roleid);
	}
}