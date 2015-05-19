<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>

<body>   
	<form method="post" id="addUserForm">
		<fieldset>
			<legend>用户基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>名称</td>
					<td><input name="username" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				  
				<tr>
					<td>年龄</td>
					<td><input name="age" class="easyui-validatebox"/></td>
				</tr>
				<tr>	
					<td>性别</td>
					<td>
						<select name="sex" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
								<option value="0">男</option>
								<option value="1">女</option>
						</select>
					</td>
				</tr>
				<tr>	
					<td>状态</td>
					<td>
						<select name="locked" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
								<option value="0">解锁</option>
								<option value="1">锁定</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>手机号</td>
					<td><input name="mobile" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<td>地址</td>
					<td><input name="address" class="easyui-validatebox"/></td>
				</tr>
				
				<tr>
					<td>电子邮件</td>
					<td><input name="email" class="easyui-validatebox" data-options="required:true,validType:'email'"/></td>
				</tr>
				
				<tr>
					<td>生日</td>
					<td><input name="birthday" class="easyui-datebox" /></td>
				</tr>
				<tr>
					<td>所属组织机构</td>
					<td><input id="setOrganization" name="organizationId"/>  </td>
				</tr>
				<tr>
					<td>所属角色</td>
					<td><input id="setRole" name="roleIds"/>  </td>
				</tr>
			</table>
		</fieldset>
	</form>  
	<script type="text/javascript">
		$(function(){
			$('#setRole').combobox({    
			    url: '${pageContext.request.contextPath}/user/getRoleCombobox',
			    valueField:'id',    
    			textField:'role',  
			    editable:false,
			    multiple:true,         //支持多选      
			    width:'200'  
			});  
			$('#setOrganization').combotree({    
			    url: '${pageContext.request.contextPath}/user/getOrganizationComboTree', 
			    method:'POST',
			    editable:false,
			    multiple:false,         //不支持多选      
			    width:'200'  
			});  
		});
	</script>
</body> 

</html>