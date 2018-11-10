<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
 	a:hover{text-decoration: underline;color: #a29f9f;}
 	.searchBar ul.searchContent li{width:250px;}
 	.searchBar ul.searchContent{height:auto;}
 	.searchBar ul.searchContent li.searchBtn{float:right;}
</style>
<form id="pagerForm" method="post" action="/sys_log">
	<input type="hidden" name="pageNum" value="${userLogs.pageNumber}" />
	<input type="hidden" name="numPerPage" value="${userLogs.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="/sys_log" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>用户名/姓名：</label>
				<input type="text" name="keywords" value="${keywords}"/>
			</li>
			<li>
				<label>模块名：</label>
				<input type="text" name="functionname" value="${functionname}"/>
			</li>
			<li>
				<label>操作内容：</label>
				<input type="text" name="content" value="${content}"/>
			</li>
			<li>
				<label>开始时间：</label>
				<input type="text" name="startTime" class="date" alt="" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="readonly"/>
			</li>
			<li>
				<label>结束时间：</label>
				<input type="text" name="endTime" class="date" alt="" dateFmt="yyyy-MM-dd HH:mm:ss" readonly="readonly"/>
			</li>
			<li  style="width:50px">	
				<div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div>
			</li>	
			
		</ul>				
	</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" width="95%" layoutH="90">
		<thead>
			<tr>				
				<th width="5%" align="center">序号</th>
				<th width="10%" align="center">用户名</th>
				<th width="10%" align="center">姓名</th>
				<th width="15%" align="center">登录ip</th>	
				<th width="10%" align="center">模块名</th>				
				<th>操作内容</th>
				<th width="15%" align="center">操作时间</th>
			</tr>
		</thead>				
		<tbody>
			<c:forEach items="${userLogs.list}" var="item" varStatus="s">
				<tr target="usrlog" rel="${item.id }">				
					<td align="center">${s.count }</td>
					<td align="center">${item.username}</td>
					<td align="center">${item.realname}</td>
					<td align="center">${item.loginip }</td>
					<td align="center">${item.functionname }</td>
					<td >${item.opcontent }</td>
					<td align="center"><fmt:formatDate value="${item.time }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>	
			</c:forEach>			
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>			
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${userLogs.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${userLogs.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${userLogs.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${userLogs.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${userLogs.totalRow}条</span>
		</div>		
		<div class="pagination" targetType="navTab" totalCount="${userLogs.totalRow}" numPerPage="${userLogs.pageSize}" pageNumShown="10" currentPage="${userLogs.pageNumber}"></div>		
	</div>
</div>
    