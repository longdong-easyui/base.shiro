<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>

<body>   
	<form method="post" id="editRoleForm">
		<input type="hidden" name="id"/>
		<fieldset>
			<legend>角色基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>角色名称</td>
					<td><input name="role" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>描述</td>
					<td><input name="description" /></td>
				</tr>
				<tr>
					<td>状态</td>
					<td><select name="available" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
								
								<option value="1">禁用</option>
								<option value="0">可用</option>
						</select>
					</td>
				</tr>
				<tr>	
					<td>拥有的资源</td>
					<td>
						<input id="setResource" name="resourceIds"/>
					</td>
				</tr>
				
			</table>
		</fieldset>
	</form>  
	<script type="text/javascript">
		$(function(){
			$('#setResource').combotree({    
			    url: '${pageContext.request.contextPath}/role/getResourceComboTree', 
			    method:'POST',
			    editable:false,
			    multiple:true,         //支持多选      
			    width:'200'  
			});  
			
			

		});
	</script>
</body> 

</html>