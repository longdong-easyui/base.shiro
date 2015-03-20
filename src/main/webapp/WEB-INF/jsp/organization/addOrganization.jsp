<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>

<body>   
	<form method="post" id="addOrganizationForm">
		<fieldset>
			<legend>资源基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>父节点</td>
					<td>
						<input id="parentOrganization" name="parentId" />  
					</td>
				</tr>
				<tr>
					<td>组织机构名称</td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true,validType:'length[0,15]'" /></td>
				</tr>
				
				<tr>
					<td>状态</td>
					<td><select name="available" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
								
								<option value="1">禁用</option>
								<option value="0">可用</option>
						</select>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>  
	<script type="text/javascript">
		$(function(){
			/**
			 * 动态获取下拉列表框的数据
			 */
			$('#parentOrganization').combobox({  
				editable:false,  
			    url:'${pageContext.request.contextPath}/organization/getParentOrganizationList',     
			    valueField:'id',    
			    textField:'name'   
			});  
			
		});
	</script>
</body> 

</html>