package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseFFiles<M extends BaseFFiles<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public M setFilename(java.lang.String filename) {
		set("filename", filename);
		return (M)this;
	}

	public java.lang.String getFilename() {
		return get("filename");
	}

	public M setFiletype(java.lang.String filetype) {
		set("filetype", filetype);
		return (M)this;
	}

	public java.lang.String getFiletype() {
		return get("filetype");
	}

	public M setFilepath(java.lang.String filepath) {
		set("filepath", filepath);
		return (M)this;
	}

	public java.lang.String getFilepath() {
		return get("filepath");
	}

	public M setUploadtime(java.util.Date uploadtime) {
		set("uploadtime", uploadtime);
		return (M)this;
	}

	public java.util.Date getUploadtime() {
		return get("uploadtime");
	}

	public M setState(java.lang.Integer state) {
		set("state", state);
		return (M)this;
	}

	public java.lang.Integer getState() {
		return get("state");
	}

	public M setEncryptname(java.lang.String encryptname) {
		set("encryptname", encryptname);
		return (M)this;
	}

	public java.lang.String getEncryptname() {
		return get("encryptname");
	}

	public M setUploadid(java.lang.Integer uploadid) {
		set("uploadid", uploadid);
		return (M)this;
	}

	public java.lang.Integer getUploadid() {
		return get("uploadid");
	}

}
