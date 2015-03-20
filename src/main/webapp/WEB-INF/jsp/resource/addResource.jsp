<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>

<body>   
	<form method="post" id="addMenuForm">
		<fieldset>
			<legend>资源基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>父节点</td>
					<td>
						<input id="parentResource" name="parentId" />  
					</td>
				</tr>
				<tr>
					<td>资源名称</td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true,validType:'length[0,15]'" /></td>
				</tr>
				<tr>
					<td>类型</td>
					<td><input id="resourceType" name="type"  data-options="required:true"/></td>
				</tr>
				<tr id="hs">
					<td>url路径</td>
					<td><input id="url" name="url" class="easyui-validatebox" data-options="required:true"/></td>
				</tr>
				
				<tr>
					<td>权限字符串</td>
					<td><input name="permission" class="easyui-validatebox" data-options="required:true"/></td>
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
			$('#parentResource').combobox({  
				editable:false,  
			    url:'${pageContext.request.contextPath}/resource/getParentResourceList',     
			    valueField:'id',    
			    textField:'name'   
			});  
			/**
			 * 动态获取资源类型
			 */
			$('#resourceType').combobox({  
				editable:false,  
			    url:'${pageContext.request.contextPath}/resource/getResourceTypeList',     
			    valueField:'name',    
			    textField:'value',
			    onSelect:function(){
			    	var v = $('#resourceType').combobox('getValue');
			    	console.info(v);
			    	if(v=='button'){
			    		$("#url").validatebox('disableValidation');
			    		$("#hs").hide();
			    	}else{
			    		$("#url").validatebox('enableValidation');
			    		$("#hs").show();
			    	}
			    }
			   
			}); 
			$('#resourceType').combobox('setValue','menu');
		});
	</script>
</body> 

</html>