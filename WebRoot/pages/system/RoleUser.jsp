<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="pagerForm" method="post" action="/sys_auth/ShowRoleUser/${roleid}">
	<input type="hidden" name="roleid" value="${roleid}" />
	<input type="hidden" name="pageNum" value="${page.pageNumber}" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form  onsubmit="return divSearch(this,'authdetail');" action="/sys_auth/ShowRoleUser/${roleid}" method="post">
	<input type="hidden" name="roleid" value="${roleid}" />	
	<div class="searchBar">
		<ul class="searchContent">			
			<li>
				<label>用户名/姓名：</label>
				<input type="text" name="keywords" value="${keywords}"/>
			</li>
			<li>
				<div class="subBar">
					<ul>
						<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>				
					</ul>
				</div>
			</li>			
		</ul>				
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/sys_auth/showaddauthuser/${roleid }" target="dialog" width="600" height="500"><span>添加</span></a></li>						
		</ul>
	</div>
	<table class="table" layoutH="112">
		<thead>
			<tr>				
				<th width="50">序号</th>
				<th width="150">姓名</th>
				<th width="150">用户名</th>
				<th width="100">状态</th>									
				<th width="50">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="s">
				<tr>				
					<td>${s.count }</td>
					<td>${item.realname }</td>
					<td>${item.username}</td>
					<td>${item.getDesc()}</td>										
					<td>
						<a  target="ajaxTodo" href="/sys_auth/deleteuser?userid=${item.id }&roleid=${roleid}" class="btnDel">删除</a>						
					</td>
				</tr>	
			</c:forEach>			
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>			
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value},'authdetail')">
				<option value="1" <c:if test="${page.pageSize eq 1 }">selected</c:if>>1</option>
				<option value="20" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRow}条</span>
		</div>		
		<div class="pagination" targetType="navTab" rel="authdetail" totalCount="${page.totalRow}" numPerPage="${page.pageSize}" pageNumShown="10" currentPage="${page.pageNumber}"></div>		
	</div>
</div>
    