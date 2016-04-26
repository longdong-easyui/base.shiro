<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>


<body>   
	<form method="post" id="addArticleForm" enctype="multipart/form-data">
		<input type="hidden" name="available" value="0"/>
		<fieldset>
			<legend>文章基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>名称</td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>	
					<td>内容</td>
					<td>
						<textarea cols="50"  name="content" rows="10"> </textarea>
						
					</td>
				</tr>
			</table>
		</fieldset>
	</form>  

</body> 

</html>