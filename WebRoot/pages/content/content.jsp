<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<Script>
	function  check_idnum(id){
	 	//var baseurl="/content/downloadUrl?fileid=21";
		/* $("#idcard_a").load(baseurl); */
		//$("#download").attr("href",baseurl); 
		$("#sid_user_file").val(id);
		var inputs = $("#sid_user_file").val();
	//	$.pdialog.open(baseurl, "checkidcard", "身份证校验", {width:400,height:200,mixable:true,minable:true,resizable:true,drawable:true,fresh:true,close:"", param:"{msg:’message’}"});
				
	}
	
	function getid(){
		if($("#sid_user_file").val()==="-1"){
			alert("请选择一个文件")
		}else{
			var baseurl="/content/downloadUrl?fileid="+$("#sid_user_file").val();
			$("#download").attr("href",baseurl)
		}
	}
	</Script>
<form id="pagerForm" method="post" action="/content/index">
	<input type="hidden" name="pageNum" value="${page.pageNumber}" /> 
	<input type="hidden" name="numPerPage" value="${page.pageSize}" /> 
</form>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="/content/index" method="post" onreset="$(this).find('select.combox').comboxReset()">
	<!-- <div class="searchBar">
		<ul class="searchContent">
			<li>
				<label>我的客户：</label>
				<input type="text"/>
			</li>
			<li>
			<select class="combox" name="province">
				<option value="">所有省市</option>
				<option value="北京">北京</option>
				<option value="上海">上海</option>
				<option value="天津">天津</option>
				<option value="重庆">重庆</option>
				<option value="广东">广东</option>
			</select>
			</li>
		</ul>
		<table class="searchContent">
			<tr>
				<td>
					我的客户：<input type="text" name="keyword" />
				</td>
				<td>
					<select class="combox" name="province">
						<option value="">所有省市</option>
						<option value="北京">北京</option>
						<option value="上海">上海</option>
						<option value="天津">天津</option>
						<option value="重庆">重庆</option>
						<option value="广东">广东</option>
					</select>
				</td>
				<td>
					建档日期：<input type="text" class="date" readonly="true" />
				</td>
			</tr>
		</table>
		<div class="subBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<li><a class="button" href="demo_page6.html" target="dialog" mask="true" title="查询框"><span>高级检索</span></a></li>
			</ul>
		</div>
	</div> -->
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add" href="/content/batchadduser" mask="true" target="dialog" rel="batchadduserdialog"><span>上传文件</span></a></li>
			<li><a class="delete" id = "download" ><span onclick="getid()" >下载</span></a></li>
			<!-- <li><a class="edit" href="demo_page4.html?uid={sid_user}" target="navTab"><span>修改</span></a></li> -->
			<li class="line">line</li>
			<!-- <li><a class="icon" href="demo/common/dwz-team.xls" target="dwzExport" targetType="navTab" title="实要导出这些记录吗?"><span>导出EXCEL</span></a></li> -->
		</ul>
	</div>
	<table class="table" width="100%" layoutH="138">
		<thead>
			<tr>
				<th width="80" align="center">序号</th>
				<th width="120" align="center">文件名</th>
				<th width="120" align="center">文件类型</th>
				<th width="100" align="center">上传时间</th>
				<th width="150" align="center">状态</th>
				<th width="10%">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="item" varStatus="s">
				<tr target="sid_user" rel="${item.id }" onclick = "check_idnum(${item.id })">	
					<td>${s.count }</td>
					<td>${item.filename }</td>
					<%-- <td>${item.brief }</td> --%>
					<td>${item.filetype}</td>
					<td><fmt:formatDate value="${item.uploadtime}" pattern="yyyy-MM-dd HH-mm-ss"/></td>
					<c:if test="${item.state==1}"><td style="color:green">正常</td> </c:if>
					<c:if test="${item.state==0}"><td style="color:red">删除</td> </c:if>
					<td>
						<a target="ajaxTodo" href="/news/deletestate/${item.id }" class="btnDel"  callback="refresh_deletenew" >删除</a>
						<a title="编辑" target="navTab" href="/news/showedit/${item.id }" class="btnEdit">编辑</a>
						<a title="查看" target="navTab" href="/news/showdetail/${item.id}" class="btnLook">查看</a>
					</td>
				</tr>	
				<input id="sid_user_file" type="hidden" name="sid_user_file" value="-1" /> 
			</c:forEach>
		</tbody>
	</table>
	<div class="panelBar">
		<div class="pages">
			<span>显示</span>
			<select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
				<option value="3" <c:if test="${page.pageSize eq 3 }">selected</c:if>>3</option>
					<option value="20" <c:if test="${page.pageSize eq 20 }">selected</c:if>>20</option>
					<option value="50" <c:if test="${page.pageSize eq 50 }">selected</c:if>>50</option>
					<option value="100" <c:if test="${page.pageSize eq 100 }">selected</c:if>>100</option>
					<option value="200" <c:if test="${page.pageSize eq 200 }">selected</c:if>>200</option>
			</select>
			<span>条，共${page.totalRow}条</span>
		</div>

	<div class="pagination" targetType="navTab" totalCount="${page.totalRow}"
			 numPerPage="${page.pageSize}" pageNumShown="10" currentPage="${page.pageNumber}"></div>
	</div>
</div>
