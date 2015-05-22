<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>


<body class="easyui-layout">   
	<form method="post" id="addSpecForm">
		<fieldset>
			<legend>规格基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>名称</td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>类型</td>
					<td><select name="type" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="1">文本</option>
							<option value="0">图片</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input name="remark" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input name="sortNo" class="easyui-validatebox"/></td>
				</tr>
			</table>
		</fieldset>
	</form>  
	
</body> 

</html>