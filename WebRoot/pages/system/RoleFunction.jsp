<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div style=" float:left; display:block; margin:10px; overflow:auto; width:300px; height:500px; overflow:auto; border:solid 1px #CCC; line-height:21px; background:#FFF;">
<div id="resultBox"></div>
<h3 >“${role.rolename }”功能如下：</h3>
<form method="post" action="/sys_auth/saveauth" class="pageForm required-validate" onsubmit="return validateCallback(this)">
<ul class="tree treeFolder treeCheck expand">
	<c:forEach items="${lv1}" var="item" varStatus="s"  >
		<li><a>${item.functionname }</a><%--  tname="functionid" tvalue="${item.id }"  根据dwz树的特点，数据库中只保存二级菜单权限(name/value不让他显示、不传值)--%>
			<ul>	
			 <c:forEach items="${item.selectLv2FunctionsByFatheridRoleid(item.id,roleid) }" var="sub" varStatus="s2">
							<li><a tname="functionid" tvalue="${sub.id }" <c:if test="${sub.hasauth != 0 }">  checked="true"</c:if>>${sub.functionname }</a></li>							
			</c:forEach>
		</ul>
	</li>
	</c:forEach>
</ul>
<input type="submit" value="保存"/>
<input type="hidden" name="roleid" value="${roleid }"/>
</form>
</div>

<div class="pageContent" style="border-left:1px #B8D0D6 solid;border-right:1px #B8D0D6 solid">
</div>