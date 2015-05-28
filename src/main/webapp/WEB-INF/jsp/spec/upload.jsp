<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>
<body>
	<form method="post" id="uploadForm" enctype="multipart/form-data">
		<input id="fb" type="text" style="width:300px" name="file" >
		<input type="hidden" name="specId" id="i_specId" value="${specId}">
		<input type="hidden" name="sortNo" id="i_sortNo" value="0">
	</form>
	<script type="text/javascript">
		
		//设置上传附件控件的属性	
		$('#fb').filebox({    
		    buttonText: '选择文件', 
		    buttonAlign: 'right' 
		});
		function upload(data){
			var path = $("#fb").val();
			if(path!=null){
				$('#uploadForm').form('submit', { 
					url : '${pageContext.request.contextPath}/spec/insertSpecDetail', 
				    success: function(data){ 
				    	var json=$.parseJSON(data); 
				    	
				    	window.parent.callback(json);
				    }    
				});
			}else{
				window.parent.callback(null);
			}
			
		}
	</script>
</body>
</html>