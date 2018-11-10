package com.demo.util;
import java.text.MessageFormat;


public class DwzJson {

	private String statusCode = "200";
	private String message = "";
	private String navTabId = "";
	private String callbackType = "";
	private String forwardUrl = "";
	private String rel="";
	private String confirmMsg="";

	public DwzJson(String message, String navTabId, String rel,String callbackType) {
		this.message = message;
		this.navTabId = navTabId;
		this.rel=rel;
		this.callbackType = callbackType;
		
	}

	public DwzJson() {
		
	}

	public static DwzJson success() {
		DwzJson dwzRenderJson = new DwzJson();		
		return dwzRenderJson;
	}

	public static DwzJson success(String successMsg) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.setMessage(successMsg);
		return dwzRenderJson;
	}
	
	public static DwzJson success(String successMsg,String navTabId) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.setMessage(successMsg);
		dwzRenderJson.setNavTabId(navTabId);
		return dwzRenderJson;
	}

	public static DwzJson success(String successMsg,String navTabId,String rel) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.setMessage(successMsg);
		dwzRenderJson.setNavTabId(navTabId);
		dwzRenderJson.setRel(rel);
		return dwzRenderJson;
	}	
	public static DwzJson timeout(String timeoutMsg,String navTabId,String rel) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.setStatusCode("301");
		dwzRenderJson.setMessage(timeoutMsg);
		dwzRenderJson.setNavTabId(navTabId);
		dwzRenderJson.setRel(rel);
		return dwzRenderJson;
	}
	/**
	 * 成功，关闭dialog，如果有navTabid更新指定的navTab,如果有rel,更新指定的rel层
	 * @param successMsg
	 * @param navTabId
	 * @param rel
	 * @return
	 */
	public static DwzJson successAndClose(String successMsg,String navTabId,String rel) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.setMessage(successMsg);
		dwzRenderJson.setNavTabId(navTabId);
		dwzRenderJson.setRel(rel);
		dwzRenderJson.setCallbackType("closeCurrent");
		return dwzRenderJson;
	}
	public static DwzJson error() {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.statusCode = "300";
		dwzRenderJson.message = "操作失败";
		return dwzRenderJson;
	}

	public static DwzJson error(String errorMsg) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.statusCode = "300";
		dwzRenderJson.message = errorMsg;
		return dwzRenderJson;
	}


	public static DwzJson refresh(String refreshNavTabId) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.navTabId = refreshNavTabId;
		return dwzRenderJson;
	}

	public static DwzJson closeCurrentAndRefresh(String refreshNavTabId) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.navTabId = refreshNavTabId;
		dwzRenderJson.callbackType = "closeCurrent";
		return dwzRenderJson;
	}
	public static DwzJson closeCurrentAndFoward(String refreshNavTabId, String fowardUrl) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.navTabId = refreshNavTabId;
		dwzRenderJson.callbackType = "closeCurrent";
		dwzRenderJson.forwardUrl = fowardUrl;
		return dwzRenderJson;
	}
	
	public static DwzJson closeCurrentAndRefresh(String refreshNavTabId, String message) {
		DwzJson dwzRenderJson = new DwzJson();
		dwzRenderJson.navTabId = refreshNavTabId;
		dwzRenderJson.message = message;
		dwzRenderJson.callbackType = "closeCurrent";
		return dwzRenderJson;
	}

	public String renderJson(){
		String dwz = "\"statusCode\":\"{0}\",\"message\":\"{1}\",\"navTabId\":\"{2}\",\"rel\":\"{3}\",\"callbackType\":\"{4}\",\"forwardUrl\":\"{5}\",\"confirmMsg\":\"{6}\"";
		dwz = "{\n" + MessageFormat.format(dwz, statusCode, message, navTabId, rel, callbackType, forwardUrl , confirmMsg) + "\n}";
		return dwz;
	}
	
	

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getNavTabId() {
		return navTabId;
	}

	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}

	public String getCallbackType() {
		return callbackType;
	}

	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getConfirmMsg() {
		return confirmMsg;
	}

	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
	}

}
