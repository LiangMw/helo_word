<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="pageContent">
    <form  action="/content/uploadFile" onsubmit="return iframeCallback(this,dialogAjaxDone);" target="ajaxTodo" class="pageForm required-validate" enctype="multipart/form-data" method="post" class="fm">
        <div class="pageFormContent" layoutH="96">
            <input type="hidden" name="orgid" value="${orgid}">
            <div class="unit">
                <label >文件：</label>
                <input name="file1" type="file" width="50px" class="fm"/>
            </div>
            
        </div>
        <div class="formBar">
            <ul>
                <!--<li><a class="buttonActive" href="javascript:;"><span>保存</span></a></li>-->
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>

</div>