package com.demo.common.model;


import com.demo.common.model.base.BaseUser;
import com.jfinal.kit.HashKit;
import com.jfinal.plugin.activerecord.Page;
import com.mysql.jdbc.StringUtils;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class User extends BaseUser<User> {
	public static final User me = new User().dao();
	
	/**
	 * 查询除了admin外的所有用户
	 */
	public Page<User> select4Manage(String keywords,int currentPage,int pageSize){
		String sql=" from t_user where username<>'admin' ";				
		if(!StringUtils.isNullOrEmpty(keywords)){
			sql+="and (username like '%"+keywords+"%'"+"or realname like '%"+keywords+"%')";
		}
		return paginate(currentPage, pageSize, "select * ", sql);
	}
	
	
	public User validateLogin(String username,String password)
		{
			User temp=getInUseByUserName(username);
			if(temp==null)
				return null;		
			String loginHash=myHash(password, temp.getStr("salt"));
			String userHash=temp.getStr("password");
			if(loginHash.equals(userHash))
				return temp;		
			return null;		
		}
	//根据用户名获取状态为在用的用户
		public User getInUseByUserName(String username){
			return findFirst("select * from t_user where state=1 and username=?", username);
		}
		
		public static  String myHash(String input,String salt){
			return HashKit.sha256(input+salt);
		}	
		
		/*
		 * 根据角色id查询角色下的所有用户
		 */
		public Page<User> selectByRoleid(Integer roleid,String keywords,int currentPage,int pageSize){
			String sql=" from t_roleuser t inner join t_user u on t.userid=u.id where  t.roleid=? ";
			if(!StringUtils.isNullOrEmpty(keywords)){
				sql+="and (username like '%"+keywords+"%'"+"or realname like '%"+keywords+"%')";
			}
			return paginate(currentPage, pageSize, "select u.*", sql,roleid);
		}
		
		/*
		 * 根据角色id查询还可以为角色下再添加哪些人
		 */
		public Page<User> selectByRoleid4Add(Integer roleid,String keywords,int currentPage,int pageSize){
			String sql=" from t_user u left join (select * from t_roleuser where roleid=?)ru on u.id=ru.userid "
					+ " where ru.userid is null and u.username<>'admin' and u.state=1 ";
			if(!StringUtils.isNullOrEmpty(keywords)){
				sql+="and (username like '%"+keywords+"%'"+"or realname like '%"+keywords+"%')";
			}
			return paginate(currentPage, pageSize, "select u.*", sql,roleid);
		}
		public String getDesc() {
			String ret="";
			Integer state=getInt("state");
			if(state==1)
			{
				ret= "在用";			
			}else if(state==-1)
			{
				ret= "停用";
			}		
			put("desc",ret);
			return ret;
		}
}
