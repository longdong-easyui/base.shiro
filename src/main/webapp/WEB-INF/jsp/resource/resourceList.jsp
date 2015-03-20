<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>
<!-- 
	问题备份信息
	问题：表格行编辑的时候，editor的type为checkbox的时候，不能带该列数据的值
	解决：该行属性里面不能有formatter属性，删除formatter属性,并且该列的值还不能是true,false
 -->
<body  class="easyui-layout">   
	  
	    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
	    	<table id="resourcedg"></table> 
	    </div> 
		<script type="text/javascript">
			var editResourceId;
			var resourcedg;
			$(function(){
				resourcedg = $('#resourcedg').treegrid({   
				
							rownumbers: true,
			                animate: true,
			                fitColumns: true, 
			                idField:'id',    
						    treeField:'name',
						    url:'${pageContext.request.contextPath}/resource/findAllResource',
						    checkOnSelect:false,
							selectOnCheck:false,   
						    columns:[[   
						     	{field:'id',title:'id',width:100,checkbox:true},    
						        {field:'name',title:'名称',width:200,editor:{type:'validatebox',options:{required:true,validType:'length[0,10]'}}},    
						        {field:'type',title:'资源类型',width:100}, 
						        {field:'url',title:'url路径',width:100},
						        {field:'permission',title:'权限字符串',width:100,editor:{type:'validatebox',options:{required:true,validType:'length[0,50]'}}},
						        {field:'availableStr',title:'状态',width:100,editor:{  
																                type:'checkbox',  
																                options:{  
																                    on: '可用',  
																                    off: '禁用'  
																                }  
																            }  
								}
						        
						    ]],
						     toolbar: [{
					            text:'保存',
					            iconCls:'icon-save',
					            handler:function(){saveResource();}
					        },{
					            text:'编辑',
					            iconCls:'icon-edit',
					            handler:function(){editResource();}
					        },{
					            text:'取消',
					            iconCls:'icon-cancel',
					            handler:function(){cancelResource();}
					        },'-',{
					            text:'新增',
					            iconCls:'icon-add',
					            handler:function(){addResource();}
					        },{
					            text:'删除',
					            iconCls:'icon-remove',
					            handler:function(){deleteResource();}
					        },'-',{
					            text:'折叠',
					            handler:function(){
					            	resourcedg.treegrid('collapseAll');
					            }
					        },{
					            text:'展开',
					            handler:function(){
					            	resourcedg.treegrid('expandAll');
					            }
					        }],
							onLoadSuccess:function(){
								//resourcedg.treegrid('collapseAll');
							},
							
							onAfterEdit:function(row,changes){
								row.children=[];
								//在用户完成编辑的时候触发。
								if(row.name !='' && row.permission !=''){
				            		 //更新到数据库
					                $.ajax({
										url : '${pageContext.request.contextPath}/resource/updateResource',  
										data : row,
										type:'POST',
										dataType : 'json',
										success : function(data) {
											//console.info(data.desc);
											 if(data.status==0){
											  	showMessage( '提示',data.desc);
											 }else{
											 	showMessage( '提示',data.desc);
											 }
										}
									});
				            	}
								
								editResourceId = undefined;
								 
							}
				});  
			});
			
	        function editResource(){
	            if (editResourceId != undefined){
	                resourcedg.treegrid('select', editResourceId);
	                return;
	            }
	            var row = resourcedg.treegrid('getSelected');
	            if (row){
	                editResourceId = row.id
	                resourcedg.treegrid('beginEdit', editResourceId);
	            }
	        	
	        }
	        function saveResource(){
	            if (editResourceId != undefined){
	            	var row = resourcedg.treegrid('getSelected');
	            	//更新成功后，关闭编辑行
					resourcedg.treegrid('endEdit', editResourceId);
					// editResourceId = undefined;
	            }
	        }
	        function cancelResource(){
	            if (editResourceId != undefined){
	                resourcedg.treegrid('cancelEdit', editResourceId);
	                editResourceId = undefined;
	            }
	        }
	        function addResource(){
				addResourceDialog =$("<div/>").dialog({
									title: '新增资源',    
								    width: 500,    
								    height:400,    
								    closed: false,    
								    cache: false,    
								    href: '${pageContext.request.contextPath}/resource/toAddResourcePage',    
								    modal: true,
								    buttons : [ {
										text : '添加',
										handler : function() {
											$('#addMenuForm').form('submit', {   
												url: '${pageContext.request.contextPath}/resource/addResource',  
											    success: function(data){ 
											    	var json=$.parseJSON(data);  
											        if (json.status==0){
											        	//刷新数据列表
											        	resourcedg.treegrid('reload');
											        	//消息提示 
											            showMessage('提示',json.desc);
											        }else{
											        	showMessage('错误',json.desc);
											        }  
											      //关闭弹出窗
										        	addResourceDialog.dialog('close');
											    }    
											}); 
										}
									} ],
									onClose:function(){
										//在窗口关闭之后触发
										addResourceDialog.dialog('destroy');
									}    
							});
			}
	       
			
			function deleteResource(){
				var rows = resourcedg.treegrid('getChecked');
				var ids = [];
				if (rows.length > 0) {
					$.messager.confirm('请确认', '确定要删除勾选记录以及它的子级元素吗？', function(r) {
						if (r) {
							for ( var i = 0; i < rows.length; i++) {
								if(rows[i]._parentId != 0){  //强制性不能删除根目录
									ids.push(rows[i].id);
								}
							}
							$.ajax({
								url : '${pageContext.request.contextPath}/resource/deleteResource',  
								data : {
									ids : ids.join(',')
								},
								dataType : 'json',
								success : function(data) {
									 
									 resourcedg.treegrid('load');
									 resourcedg.treegrid('unselectAll');
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