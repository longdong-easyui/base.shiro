<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
  <style scoped>
        .f1{
            width:200px;
        }
   </style>
</head>
 

<body>   
 	<div class="easyui-panel" title="设置文章类型" style="width:300px;padding:10px;">
        <form 	id="ff"
        		action="${pageContext.request.contextPath}/article/addArticleType"
         		method="post" >
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input name="name" class="f1 easyui-textbox"  data-options="required:true"></input></td>
                </tr>
                <tr>
                    <td>code:</td>
                    <td><input name="code" class="f1 easyui-textbox"  data-options="required:true"></input></td>
                </tr>
                
                <tr>
                   <td colspan="2"> <button type="submit">添加</button></td>
                </tr>
            </table>
        </form>
    </div>
  
    <script type="text/javascript">
	    $(function(){
	    	 $('#ff').form({
	             success:function(data){
	            	 var json=$.parseJSON(data); 
	            	 //console.info(json.status);
	            	 if(json.status==0){
	            		 $.messager.alert('消息','添加成功', 'info');
	            		 $('#ff input').val('');
	            	 }else{
	            		 $.messager.alert('消息','添加失败', 'info');	 
	            		
	            	 }
	             }
	        });
	    	
	    });
	  
    </script>

	
</body> 

</html>