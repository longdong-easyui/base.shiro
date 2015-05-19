<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>

</head>

<body>

 
	<form method="post" id="editBrandForm">
		<input type="hidden" name="id"/>
		<fieldset>
			<legend>品牌基本信息</legend>
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
					<td>logo</td>
					<td>
					<img id="logo" alt="logo" >
					<input id="fb" type="text" style="width:300px" name="file">
					</td>
				</tr>
				<tr>
					<td>网址</td>
					<td><input name="url" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input name="sortNo" class="easyui-validatebox"/></td>
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
	
	<script type="text/javascript">
		
		//设置上传附件控件的属性	
		$('#fb').filebox({    
		    buttonText: '选择文件', 
		    buttonAlign: 'right' 
		});
		
	</script>
</body> 

</html>