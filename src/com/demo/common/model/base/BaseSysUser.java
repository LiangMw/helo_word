package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseSysUser<M extends BaseSysUser<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setGuid(java.lang.String guid) {
		set("guid", guid);
		return (M)this;
	}

	public java.lang.String getGuid() {
		return get("guid");
	}

	public M setUsername(java.lang.String username) {
		set("username", username);
		return (M)this;
	}

	public java.lang.String getUsername() {
		return get("username");
	}

	public M setIdnumber(java.lang.String idnumber) {
		set("idnumber", idnumber);
		return (M)this;
	}

	public java.lang.String getIdnumber() {
		return get("idnumber");
	}

	public M setTel(java.lang.Double tel) {
		set("tel", tel);
		return (M)this;
	}

	public java.lang.Double getTel() {
		return get("tel");
	}

	public M setPassword(java.lang.String password) {
		set("password", password);
		return (M)this;
	}

	public java.lang.String getPassword() {
		return get("password");
	}

	public M setSalt(java.lang.String salt) {
		set("salt", salt);
		return (M)this;
	}

	public java.lang.String getSalt() {
		return get("salt");
	}

	public M setCreatetime(java.util.Date createtime) {
		set("createtime", createtime);
		return (M)this;
	}

	public java.util.Date getCreatetime() {
		return get("createtime");
	}

	public M setUpdatetime(java.util.Date updatetime) {
		set("updatetime", updatetime);
		return (M)this;
	}

	public java.util.Date getUpdatetime() {
		return get("updatetime");
	}

}
