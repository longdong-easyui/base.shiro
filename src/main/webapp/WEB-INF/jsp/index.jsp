<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<title>main page</title>
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
<script type="text/javascript">
	var mainMenu;
	$(function(){
		
		//生成菜单树	
		mainMenu = $('#mainMenu').tree({
			url : '${pageContext.request.contextPath}/resource/findMenuTree', 
			parentField : 'parentId',
			onClick : function(node) {
				//console.info(node.url);  
				if(node.url != ''){
					//单击节点进行添加选项卡
					var opt = {    
					    title:node.text,    
					    href: '${pageContext.request.contextPath}'+node.url,    
					    closable:true,    
					    tools:[{    
					        iconCls:'icon-mini-refresh',    
					        handler:function(){    
					            //获取选中的选项卡对象
					            var selectTab = $('#dataTab').tabs('getSelected');  
					            //调用它的父对象Panel的刷新方法
					            selectTab.panel('refresh');      
					        }    
					    }]    
					};
					//判断选项卡是否存在
					if($('#dataTab').tabs('exists',opt.title)){
						$('#dataTab').tabs('select',opt.title);
					}else{
						addTab(opt);
					}
				}
			}
		});
	});
	
	function addTab(options){
		$('#dataTab').tabs('add',options);
	}
</script>
</head>

<body class="easyui-layout">   
    <div data-options="region:'north'" style="height:50px">
    	欢迎[<shiro:principal/>]        | <a href="${pageContext.request.contextPath}/logout">退出</a>
    </div>   
    <div data-options="region:'center'">   
        <div class="easyui-layout" data-options="fit:true">   
        	<!-- 左侧菜单导航栏 -->
            <div data-options="region:'west',collapsed:false" style="width:180px">
            	<div id="menu" class="easyui-accordion" data-options="fit:true">   
				    <div title="菜单树" data-options="iconCls:'icon-save'" style="overflow:auto;padding:10px;">   
				        <ul id="mainMenu"></ul>
				    </div>   
				    <div title="测试模块" data-options="iconCls:'icon-reload',selected:true" style="padding:10px;">   
				        
				    </div>   
				    <div title="title3">   
				        
				    </div>   
				</div>  
            </div>   
            <!-- 选项卡数据列表显示 -->
            <div data-options="region:'center'">
            	<div id="dataTab" class="easyui-tabs" data-options="fit:true">   
				    <div title="首页" style="padding:20px">   
				        welcome  you !   
				    </div>  
				</div> 
            </div>   
        </div>   
    </div>   
</body>  


</html>