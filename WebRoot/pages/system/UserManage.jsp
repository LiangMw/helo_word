<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<form id="pagerForm" method="post" action="/sys_user">
	<input type="hidden" name="pageNum" value="${uinfo.pageNumber}" />
	<input type="hidden" name="numPerPage" value="${uinfo.pageSize}" />
	<input type="hidden" name="orderField" value="${param.orderField}" />
	<input type="hidden" name="orderDirection" value="${param.orderDirection}" />
</form>

<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return navTabSearch(this);" action="/sys_user" method="post">
	<div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>用户名/姓名：</label>
				<input type="text" name="keywords" value="${keywords}"/>
			</li>
			<!-- <li>
				<label>年龄：</label>
				<input type="text" name="age" />
			</li> -->
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
			<li><a class="add" href="/sys_user/showadd" target="dialog"><span>添加</span></a></li>		
			<li><a class="icon" href="/sys_user/resetpass/{sid_user}" target="ajaxTodo" title="确定为选中用户重置密码吗?"><span>重置密码</span></a></li>			
		</ul>
	</div>
	<table class="table" width="95%" layoutH="112">
		<thead>
			<tr>				
				<th width="5%">序号</th>
				<th width="10%" >姓名</th>
				<th width="10%">用户名</th>
				<%--<th width="40"  align="center" orderField="sex">性别</th> --%>
				<th width="10%" align="center" orderField="state">状态</th>
				<th width="30%">备注</th>				
				<th width="10%">建档日期</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${uinfo.list}" var="item" varStatus="s">
				<tr target="sid_user" rel="${item.id }">				
					<td align="center">${s.count }</td>
					<td align="center">${item.realname }</td>
					<td align="center">${item.username}</td>
					<%--<td>${item.sex }</td>--%>
					<c:if test="${item.getDesc() eq '在用'}"  ><td style="color:green" align="center">${item.getDesc()}</td></c:if>
					<c:if test="${item.getDesc() eq '停用'}" ><td align="center">${item.getDesc()}</td></c:if>
					<td align="center">${item.memo }</td>
					<td align="center"><fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td align="center">
						<a title="删除" target="ajaxTodo" href="/sys_user/delete/${item.id }" class="btnDel" title="确定删除该用户吗？">删除</a>
						<a title="编辑" target="dialog" href="/sys_user/showedit/${item.id }" class="btnEdit">编辑</a>
					</td>
				</tr>	
			</c:forEach>			
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>每页显示</span>			
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="20" <c:if test="${uinfo.pageSize eq 20 }">selected</c:if>>20</option>
				<option value="50" <c:if test="${uinfo.pageSize eq 50 }">selected</c:if>>50</option>
				<option value="100" <c:if test="${uinfo.pageSize eq 100 }">selected</c:if>>100</option>
				<option value="200" <c:if test="${uinfo.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${uinfo.totalRow}条</span>
		</div>		
		<div class="pagination" targetType="navTab" totalCount="${uinfo.totalRow}" numPerPage="${uinfo.pageSize}" pageNumShown="10" currentPage="${uinfo.pageNumber}"></div>		
	</div>
</div>
    