<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="pageContent">	
	<form method="post" action="/sys_user/addedit" class="pageForm required-validate"  onsubmit="return validateCallback(this, dialogAjaxDone)">
		<div class="pageFormContent" layoutH="58">
			<div class="unit">
				<label>用户名：</label>
				<input type="text" value="${model.username }" name="user.username" size="30" minlength="1" maxlength="20" class="required<c:if test="${model.id!=null }"> disabled</c:if>" />				
			</div>
			<div class="unit">
				<label>姓名：</label>
				<input type="text" value="${model.realname }" name="user.realname" size="30" minlength="1" maxlength="20" class="required" />
			</div>			
			<c:if test="${model.id!=null }">
			<div class="unit">
				<label>用户状态：</label>				
				<select name="user.state" class="combox">
					<option value="1" <c:if test="${model.state eq 1 }">selected</c:if>>在用</option>
					<option value="-1" <c:if test="${model.state eq -1 }">selected</c:if>>停用</option>
				</select>
			</div>			
			</c:if>
			<div class="unit">
				<label>备注：</label>
				<textarea name="user.memo"  cols="50" rows="2">${model.memo }</textarea>				
			</div>			
			<input type="hidden" name="user.id" value="${model.id}" />								
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">提交</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
	
</div>