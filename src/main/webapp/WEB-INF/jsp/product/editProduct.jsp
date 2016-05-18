<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>

</head>

<body>

	<form method="post" id="editProductForm">
		<input type="hidden" name="id"/>
		<fieldset>
			<legend>产品基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>名称</td>
					<td><input name="productName" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input name="note" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>分类</td>
					<td><input name="category" class="easyui-validatebox"/>
					</td>
				</tr>
				
				<tr>
					<td>原价</td>
					<td><input name="oldPrice" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<td>现价</td>
					<td><input name="nowPrice" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input name="sortNo" class="easyui-validatebox"/></td>
				</tr>
				
			</table>
		</fieldset>
	</form>  
	
	<script type="text/javascript">
		
		//设置上传附件控件的属性	
		$('#fb').filebox({    
		    buttonText: '选择文件', 
		    buttonAlign: 'right' 
		});
		
	</script>
</body> 

</html>