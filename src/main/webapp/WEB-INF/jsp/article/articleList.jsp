<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>

<body  class="easyui-layout">   
	  
		<div data-options="region:'north',title:'查询条件',split:false" style="height:50px;padding:10px;">
			<form id="searchForm">
				<table style="font-size:12px;">
					<tr>
						<td>名称:</td>
						<td><input type="text" name="title" class="easyui-validatebox"/></td>
						
						<td align="right">
							<a id="btn" href="#" onclick="searchFun();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
							<a id="btn" href="#" onclick="clearFun();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">清空</a>
						</td>
					</tr>
				</table>
			</form>
		</div>   
      	<div id="article_toolbar">
      		<table>
      			<tr>
    				<td>
      					<a id="btn" href="#" onclick="insertFun();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
      				</td>
      				<td>
      				<div class="datagrid-btn-separator"></div>
      				</td>
    						<td>
      					<a id="btn" href="#" onclick="editFun();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
      				</td>
      				<td>
      				<div class="datagrid-btn-separator"></div>
      				</td>
     					<td>
      					<a id="btn" href="#" onclick="deleteFun();" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
      				</td>
      			</tr>
      		</table>
      	</div>
	    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
	    	
            <div id="tt" class="easyui-tabs" data-options="fit:true,plain:true" style="width:700px;height:450px">
		          <div title="文章列表" style="padding:5px;">
		         	<table id="articledg"></table>
		         </div>
		         <div title="新增文章" style="padding:5px;">
		         	 <iframe scrolling="yes" 
		         	 		 frameborder="0"  
		         	 		 src="${pageContext.request.contextPath}/article/toAddArticlePage" 
		         	 		 style="width:100%;height:100%;">
		         	 </iframe>
		         </div>
		         <div title="设置文章分类" style="padding:5px;" 
		         		data-options="href:'${pageContext.request.contextPath}/article/setArticleTypePage',closable:false" >
		         </div>
		        
		    </div>
           
	    </div> 
		<script type="text/javascript">
			var articledg;
			$(function(){
				articledg = $('#articledg').datagrid({ 
					fitColumns:true,
					idField:'id', 
					rownumbers : true,
				    url:'${pageContext.request.contextPath}/article/findAllArticle',  
				    pagination : true,  
				    sortName : 'id',
					sortOrder : 'desc',
					checkOnSelect:false,
					selectOnCheck:false,
					pageSize : 10,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ], 
				    columns:[[    
				        {field:'id',title:'id',width:50,checkbox:true},    
				        {field:'title',title:'标题',width:70},
				        {field:'subTitle',title:'副标题',width:70}, 
				        {field:'typeName',title:'文章类型',width:70},
				        {field:'availableStr',title:'状态',width:50},
				        {field:'isTopStr',title:'是否置顶',width:50},
				        {field:'createdDate',title:'创建时间',width:100}
				    ]],
				    toolbar: '#article_toolbar',
				    onSelect:function(rowIndex,rowData){
				    	articledg.datagrid('unselectAll');
				    } 
				});
			});
			
			//查询方法
			function searchFun(){
				articledg.datagrid('load',base.serializeObject($('#searchForm')));
			}
			//清空查询表单
			function clearFun(){
				$('#searchForm input').val('');
				articledg.datagrid('load',{});
			}
			
			/**
			 * 新增函数
			 * @returns {} 
			 */
			var addarticleDialog;
			function insertFun(){
				addarticleDialog =$("<div/>").dialog({
									title: '新增',    
								    width: 1000,    
								    height:600,    
								    closed: false,    
								    cache: false,    
								    href: '${pageContext.request.contextPath}/article/toAddarticlePage',    
								    modal: true,
								    buttons : [ {
										text : '添加',
										handler : function() {
											$('#addarticleForm').form('submit', {   
												url: '${pageContext.request.contextPath}/article/addarticle',  
											    success: function(data){ 
											    	var json=$.parseJSON(data);  
											    	
											        if (json.status==0){
											        	//刷新数据列表,效率较低
											        	//$('#dg').datagrid('reload');
											        	//将新增的数据直接添加到数据列表中
											        	articledg.datagrid('insertRow',{index:0,row:json.array});
											        	//消息提示 
											            showMessage('提示',json.desc);
											           
											        }else{
											        	showMessage('错误',json.desc);
											        } 
											        //关闭弹出窗
										        	addarticleDialog.dialog('close');
										        	
											    }    
											}); 
										}
									}],
									onClose:function(){
										//在窗口关闭之后触发
										addarticleDialog.dialog('destroy');
									}  
							});
			}
			
			/**
			 * 编辑函数
			 * @returns {} 
			 */
			var editarticleDialog;
			function editFun(){
				//首先判断是否有勾选记录
				var rows = articledg.datagrid('getChecked');
				
				//获取编辑行的索引值，用于编辑成功后直接更新该行的数据
				var rowindex = articledg.datagrid('getRowIndex',rows[0]);
				if(rows.length==1){
					editarticleDialog =$("<div/>").dialog({
								title: '编辑',    
							    width: 1000,    
							    height:600,    
							    closed: false,    
							    cache: false,  
							    content:'<iframe scrolling="yes" id="editframe" name="editframe1" ' 
				         	 		 +'frameborder="0" '  
				         	 		 +'src="${pageContext.request.contextPath}/article/toEditArticlePage?id='+ rows[0].id +'" '
				         	 		 +'style="width:100%;height:100%;"> '
				         	 		 +'</iframe> ',
							    modal: true,
								
								onClose:function(){
									//在窗口关闭之后触发
									editarticleDialog.dialog('destroy');
								}
								  
					});
				}else if(rows.length>1){
					$.messager.alert('提示','同一时间只能编辑一条记录！','error');
				}else{
					$.messager.alert('提示','请选择要编辑的记录！','error');
				}
			}
			/**
			 * 删除函数
			 * @returns {} 
			 */
			function deleteFun(){
				var rows = articledg.datagrid('getChecked');
				var ids = [];
				if (rows.length > 0) {
					$.messager.confirm('请确认', '确定要删除勾选记录吗？', function(r) {
						if (r) {
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$.ajax({
								url : '${pageContext.request.contextPath}/article/deleteArticle',  
								data : {
									ids : ids.join(',')
								},
								dataType : 'json',
								success : function(data) {
									 
									articledg.datagrid('load');
									articledg.datagrid('unselectAll');
									 if(data.status==0){
									  	showMessage( '提示',data.desc+'删除'+data.array+'条');
									 }else{
									 	showMessage( '提示',data.desc);
									 }
									
								}
							});
						}
					});
				} else {
					$.messager.alert('提示', '请勾选要删除的记录！', 'error');
				}
			}
			/**
			 * 提示消息
			 * @param {} title
			 * @param {} msg
			 * @returns {} 
			 */
			function showMessage(title,msg){
				$.messager.show({
					title:title,
					msg:msg,
					showType:'slide',
					timeout:5000
				});
			}
		</script>
</body> 

</html>