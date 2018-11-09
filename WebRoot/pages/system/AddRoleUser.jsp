<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form id="pagerForm" method="post" action="/sys_auth/showaddauthuser/${roleid }" >
	<input type="hidden" name="pageNum" value="${page.pageNumber}" />
	<input type="hidden" name="numPerPage" value="${page.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
	<input type="hidden" name="roleid" value="${roleid}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dialogSearch(this);" action="/sys_auth/showaddauthuser/${roleid }"   method="post">
	<input type="hidden" name="roleid" value="${roleid}" />
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>用户名/姓名：</label>
				<input type="text" name="keywords" value="${keywords}"/>
				<button type="submit">检索</button>
			</li>						
		</ul>				
	</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<%-- <ul class="toolBar">					
			<li><a class="button" href="/auth/addroleusers/${roleid }" rel="ids" target="selectedTodo" targetType="dialog" ><span>确 定</span></a></li>						
		</ul> --%>
		<a class="button" href="/sys_auth/addroleusers/${roleid }" rel="ids" target="selectedTodo" targetType="dialog" ><span>确 定</span></a>		
	</div>	
	
	<table class="table"  layoutH="112">
		<thead>
			<tr>		
				<th width="40"><input type="checkbox" group="ids" class="checkboxCtrl"></th>		
				<th width="80">序号</th>
				<th width="120">姓名</th>
				<th width="120">用户名</th>				
				<th width="120" align="center" orderField="state">状态</th>						
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="s">
				<tr target="sid_user"  rel="${item.id }">				
					<td><input name="ids" value="${item.id }" type="checkbox"  ></td>
					<td>${s.count }</td>
					<td>${item.realname }</td>
					<td>${item.username}</td>					
					<td>${item.getDesc()}</td>									
				</tr>	
			</c:forEach>			
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>			
			<select class="combox" name="numPerPage" onchange="dialogPageBreak({numPerPage:this.value})">
				<option value="3" <c:if test="${page.pageSize eq 3 }">selected</c:if>>3</option>
				<option value="20" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRow}条</span>
		</div>		
		<div class="pagination" targetType="dialog" totalCount="${page.totalRow}" numPerPage="${page.pageSize}" pageNumShown="10" currentPage="${page.pageNumber}"></div>		
	</div>
</div>
    