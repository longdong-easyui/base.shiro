<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/static/kindeditor-4.1.11/themes/default/default.css" />
<script charset="utf-8" src="${pageContext.request.contextPath}/static/kindeditor-4.1.11/kindeditor-all.js"></script>
<script charset="utf-8" src="${pageContext.request.contextPath}/static/kindeditor-4.1.11/lang/zh-CN.js"></script>

</head>


<body>   
	
	 <c:if test="${param.status==1}">
	 	<div style="color:red;">${param.desc}</div>
	 </c:if>
 	<c:if test="${param.status==0}">
	 	<div style="color:green;"> ${param.desc}</div>
	 </c:if>
	<form   method="post" 
			id="editArticleForm" 
			enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/article/editArticle">
		<input type="hidden" name="id" value="${article.id}"/>	
		
		<fieldset>
			<legend>文章基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>标题</td>
					<td><input name="title" value="${article.title}" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>副标题</td>
					<td><input name="subTitle" value="${article.subTitle}" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>文章类型</td>
					<td><input id="setType" name="type" value="${article.type}"/></td>
				</tr>
				<tr>
					<td>缩略图</td>
					<td>
					<input id="fb" type="text" style="width:300px" name="file" >
					<img alt="缩略图" id="thumd" src="${pageContext.request.contextPath}/article/findThumdById?id=${article.id}">
					</td>
				</tr>
				<tr>
					<td>状态</td>
					<td><select id="ava" name="available" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
								
								<option value="1">禁用</option>
								<option value="0">可用</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>是否置顶</td>
					<td><select id="isTop" name="isTop" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
								
								<option value="1">不置顶</option>
								<option value="0">置顶</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input name="sortNo" value="${article.sortNo}" class="easyui-validatebox"/></td>
				</tr>
				<tr>	
					<td>文章内容</td>
					<td>
						<textarea name="content" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">
							${article.contentStr}
						</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2"> <button type="submit">编辑</button></td>
				</tr>
			</table>
		</fieldset>
	</form>  
	<script type="text/javascript">
		var editor;
		KindEditor.ready(function(K) {
			editor = K.create('textarea[name="content"]', {
				resizeType : 1,
				allowPreviewEmoticons : false,
				allowImageUpload : false,
				items : [
					'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
					'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
					'insertunorderedlist', '|', 'emoticons', 'image', 'link']
			});
			
		});
		$('#ava').val('${article.available}');
		$('#isTop').val('${article.isTop}');
		
		$('#setType').combobox({
		    url:'${pageContext.request.contextPath}/article/findAllArticleType',
		    valueField:'id',
		    textField:'name',
		    required:true
		});
		//设置上传附件控件的属性	
		$('#fb').filebox({    
		    buttonText: '选择文件', 
		    buttonAlign: 'right' 
		});
		
		
	</script>
</body> 

</html>
