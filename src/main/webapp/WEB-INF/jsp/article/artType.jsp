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
	<form method="post" id="setArtTypeForm" enctype="multipart/form-data">
		
		<fieldset>
			<legend>设置文章类型</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>类型名称</td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>	
					<td>类型Code</td>
					<td>
						<input name="code" class="easyui-validatebox" data-options="required:true" />
					</td>
				</tr>
			</table>
		</fieldset>
	</form>  

</body> 

</html>