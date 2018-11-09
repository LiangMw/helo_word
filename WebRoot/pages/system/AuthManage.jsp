<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="pageContent">
<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/sys_auth/showaddrolem" target="dialog" width="400" height="150"><span>添加</span></a></li>		
		</ul>
	</div>
	<div id="authRole" class="unitBox"
		style="float: left; display: block; overflow: auto; width: 360px;">		
		<div class="pageContent"
			style="border-left: 1px #B8D0D6 solid; border-right: 1px #B8D0D6 solid">
			<table class="table" style="width: 99%" layoutH="138">
				<thead>
					<tr>
						<th width="30">序号</th>
						<th>角色名称</th>		
						<th>权限控制</th>	
						<th width="60">操作</th>									
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${rlist}" var="item" varStatus="s">
						<tr>
							<td>${s.count }</td>
							<td>${item.rolename }</td>
							<td>
								<a href="/sys_auth/ShowDetail/${item.id}" target="ajax" rel="authdetail" class="link">角色功能管理</a>
								<a href="/sys_auth/ShowRoleUser/${item.id}" target="ajax" rel="authdetail"  class="link">角色人员管理</a>
							</td>	
							<td ><!-- callback="refresh_deleterolem" -->
								<a target="ajaxTodo" href="/sys_auth/deleterolem/${item.id}" class="btnDel" title="该角色下的所有信息都将被删除<br/>确定删除吗？">删除</a>
							    <a title="编辑角色名称" target="dialog" href="/sys_auth/showedit/${item.id}" class="btnEdit" >编辑</a>
							</td>							
						</tr>
					</c:forEach>					
				</tbody>
			</table>
		</div>
	</div>
	
	<div id="authdetail" class="unitBox" style="margin-left: 310px;">		
	</div>	
</div>