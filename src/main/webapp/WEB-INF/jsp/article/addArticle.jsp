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
<script>
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
</script>
</head>


<body>   
	
	 <c:if test="${param.status==1}">
	 	<div style="color:red;">${param.desc}</div>
	 </c:if>
 	<c:if test="${param.status==0}">
	 	<div style="color:green;"> ${param.desc}</div>
	 </c:if>
	<form   method="post" 
			id="addArticleForm" 
			enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/article/addArticle">
			
		<input type="hidden" name="available" value="0"/>
		<input type="hidden" name="isTop" value="1"/>
		<input type="hidden" name="sortNo" value="1"/>
		<fieldset>
			<legend>文章基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>标题</td>
					<td><input name="title" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>副标题</td>
					<td><input name="subTitle" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>文章类型</td>
					<td><input name="type" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td>缩略图</td>
					<td>
					<input id="fb" type="text" style="width:300px" name="file" >
					</td>
				</tr>
				<tr>	
					<td>文章内容</td>
					<td>
						
						<textarea name="content" cols="100" rows="8" style="width:700px;height:200px;visibility:hidden;">
						</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2"> <button type="submit">添加</button></td>
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
		/* function addFun(){
			
			$('#addArticleForm').form('submit', { 
				url : '${pageContext.request.contextPath}/article/addArticle', 
			    success: function(data){ 
			    	var json=$.parseJSON(data); 
			    	
			        if (json.status==0){
			        	alert("文章添加成功,继续添加");
			        	window.parent.callback();
			        }else{
			        	alert(json.desc);
			        } 
			    	
			    }    
			});
			
		} */
		
	</script>
</body> 

</html>