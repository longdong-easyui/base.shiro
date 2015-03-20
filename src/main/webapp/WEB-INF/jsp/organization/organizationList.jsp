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
	    	<table id="organizationdg"></table> 
	    </div> 
		<script type="text/javascript">
			var editOrganizationId;
			var organizationdg;
			$(function(){
				organizationdg = $('#organizationdg').treegrid({   
				
							rownumbers: true,
			                animate: true,
			                fitColumns: true, 
			                idField:'id',    
						    treeField:'name',
						    url:'${pageContext.request.contextPath}/organization/findAllOrganization',
						    checkOnSelect:false,
							selectOnCheck:false,   
						    columns:[[   
						     	{field:'id',title:'id',width:100,checkbox:true},    
						        {field:'name',title:'名称',width:200,editor:{type:'validatebox',options:{required:true,validType:'length[0,10]'}}},    
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
					            handler:function(){saveOrganization();}
					        },{
					            text:'编辑',
					            iconCls:'icon-edit',
					            handler:function(){editOrganization();}
					        },{
					            text:'取消',
					            iconCls:'icon-cancel',
					            handler:function(){cancelOrganization();}
					        },'-',{
					            text:'新增',
					            iconCls:'icon-add',
					            handler:function(){addOrganization();}
					        },{
					            text:'删除',
					            iconCls:'icon-remove',
					            handler:function(){deleteOrganization();}
					        },'-',{
					            text:'折叠',
					            handler:function(){
					            	organizationdg.treegrid('collapseAll');
					            }
					        },{
					            text:'展开',
					            handler:function(){
					            	organizationdg.treegrid('expandAll');
					            }
					        }],
							onLoadSuccess:function(){
								//organizationdg.treegrid('collapseAll');
							},
							onAfterEdit:function(row,changes){
								console.info(row);
								row.children=[];
								//更新到数据库
				                $.ajax({
									url : '${pageContext.request.contextPath}/organization/updateOrganization',  
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
				            	
								editOrganizationId = undefined;
							}
				});  
			});
			
	        function editOrganization(){
	            if (editOrganizationId != undefined){
	                organizationdg.treegrid('select', editOrganizationId);
	                return;
	            }
	            var row = organizationdg.treegrid('getSelected');
	            if (row){
	                editOrganizationId = row.id
	                organizationdg.treegrid('beginEdit', editOrganizationId);
	            }
	        	
	        }
	        function saveOrganization(){
	            if (editOrganizationId != undefined){
	            	var row = organizationdg.treegrid('getSelected');
	            	//更新成功后，关闭编辑行
					organizationdg.treegrid('endEdit', editOrganizationId);
					// editOrganizationId = undefined;
	            }
	        }
	        function cancelOrganization(){
	            if (editOrganizationId != undefined){
	                organizationdg.treegrid('cancelEdit', editOrganizationId);
	                editOrganizationId = undefined;
	            }
	        }
	        function addOrganization(){
				addOrganizationDialog =$("<div/>").dialog({
									title: '新增组织结构',    
								    width: 500,    
								    height:400,    
								    closed: false,    
								    cache: false,    
								    href: '${pageContext.request.contextPath}/organization/toAddOrganizationPage',    
								    modal: true,
								    buttons : [ {
										text : '添加',
										handler : function() {
											$('#addMenuForm').form('submit', {   
												url: '${pageContext.request.contextPath}/organization/addOrganization',  
											    success: function(data){ 
											    	var json=$.parseJSON(data);  
											        if (json.status==0){
											        	//刷新数据列表
											        	organizationdg.treegrid('reload');
											        	//消息提示 
											            showMessage('提示',json.desc);
											        }else{
											        	showMessage('错误',json.desc);
											        }  
											      //关闭弹出窗
										        	addOrganizationDialog.dialog('close');
											    }    
											}); 
										}
									} ],
									onClose:function(){
										//在窗口关闭之后触发
										addOrganizationDialog.dialog('destroy');
									}    
							});
			}
	       
			
			function deleteOrganization(){
				var rows = organizationdg.treegrid('getChecked');
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
								url : '${pageContext.request.contextPath}/organization/deleteOrganization',  
								data : {
									ids : ids.join(',')
								},
								dataType : 'json',
								success : function(data) {
									 
									 organizationdg.treegrid('load');
									 organizationdg.treegrid('unselectAll');
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