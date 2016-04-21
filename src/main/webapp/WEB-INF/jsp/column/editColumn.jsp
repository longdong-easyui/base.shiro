<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>

</head>

<body>
	<form method="post" id="editColumnForm">
		<input type="hidden" name="id"/>
		<fieldset>
			<legend>栏目信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>父栏目</td>
					<td>
						<input id="parentColumn" name="parentId" />  
					</td>
				</tr>
				<tr>
					<td>名称</td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>网址</td>
					<td><input name="url" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input name="sortNo" class="easyui-validatebox"/></td>
				</tr>
			</table>
		</fieldset>
	</form>  
	<script type="text/javascript">
		$(function(){
			/**
			 * 动态获取下拉列表框的数据
			 */
			$('#parentColumn').combotree({  
				editable:false,  
			    url:'${pageContext.request.contextPath}/column/getColumnList',     
			    required: true  
			});  
			
		});
	</script>
</body> 

</html>