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
			<form id="searchRoleForm">
				<table>
					<tr>
						<td>角色名称</td>
						<td><input type="text" name="role" class="easyui-validatebox"/></td>
						<td align="right">
							<a id="btn" href="#" onclick="searchFun();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
							<a id="btn" href="#" onclick="clearFun();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">清空</a>
						</td>
					</tr>
				</table>
			</form>
		</div>   
      	<div id="role_toolbar">
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
	    	<table id="roledg"></table>  
	    	
	    </div> 
		<script type="text/javascript">
			var roledg;
			$(function(){
				roledg = $('#roledg').datagrid({ 
					
							fitColumns:true,
							idField:'id', 
							rownumbers : true,
						    url:'${pageContext.request.contextPath}/role/findAllRole',  
						    pagination : true,  
						    sortName : 'id',
							sortOrder : 'desc',
							checkOnSelect:false,
							selectOnCheck:false,
							pageSize : 10,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ], 
						    columns:[[    
						        {field:'id',title:'id',width:100,checkbox:true},    
						        {field:'role',title:'角色名称',width:100},    
						        {field:'description',title:'描述',width:100},
						        {field:'resourceIdsName',title:'资源',width:200},
						        {field:'availableStr',title:'状态',width:100,editor:{type:'checkbox',options:{ on: '可用',off: '禁用'} }  }   
						    ]],
						    toolbar: '#role_toolbar'
						        
						});
			});
			//查询方法
			function searchFun(){
				roledg.datagrid('load',base.serializeObject($('#searchRoleForm')));
			}
			//清空查询表单
			function clearFun(){
				$('#searchRoleForm input').val('');
				roledg.datagrid('load',{});
			}
			
			/**
			 * 新增函数
			 * @returns {} 
			 */
			var addRoleDialog;
			function insertFun(){
				addRoleDialog =$("<div/>").dialog({
									title: '新增',    
								    width: 500,    
								    height:400,    
								    closed: false,    
								    cache: false,    
								    href: '${pageContext.request.contextPath}/role/toAddRolePage',    
								    modal: true,
								    buttons : [ {
										text : '添加',
										handler : function() {
											$('#addRoleForm').form('submit', {   
												url: '${pageContext.request.contextPath}/role/addRole',  
											    success: function(data){ 
											    	var json=$.parseJSON(data);   
											         
											        if (json.status==0){
											        	//刷新数据列表,效率较低
											        	//$('#dg').datagrid('reload');
											        	//将新增的数据直接添加到数据列表中
											        	roledg.datagrid('insertRow',{index:0,row:json.array});
											        	//消息提示 
											            showMessage('提示',json.desc);
											           
											        }else{
											        	showMessage('错误',json.desc);
											        } 
											        //关闭弹出窗
										        	addRoleDialog.dialog('close');
										        	
											    }    
											}); 
										}
									} ],
									onClose:function(){
										//在窗口关闭之后触发
										addRoleDialog.dialog('destroy');
									}  
							});
			}
			/**
			 * 编辑函数
			 * @returns {} 
			 */
			var editRoleDialog;
			function editFun(){
				//首先判断是否有勾选记录
				var rows = roledg.datagrid('getChecked');
				//获取编辑行的索引值，用于编辑成功后直接更新该行的数据
				var rowindex = roledg.datagrid('getRowIndex',rows[0]);
				if(rows.length==1){
				
					editRoleDialog =$("<div/>").dialog({
								title: '编辑',    
							    width: 500,    
							    height:400,    
							    closed: false,    
							    cache: false,    
							    href: '${pageContext.request.contextPath}/role/toEditRolePage',    
							    modal: true,
							    buttons : [ {
									text : '编辑',
									handler : function() {
										$('#editRoleForm').form('submit', {   
												url: '${pageContext.request.contextPath}/role/editRole',  
											    success: function(data){ 
											    	var json=$.parseJSON(data); 
											    	
											        if (json.status==0){
											        	//刷新数据列表,效率较低
											        	//$('#dg').datagrid('reload');
											        	//将编辑的数据直接更新数据列表中对应的列
											        	roledg.datagrid('updateRow',{index:rowindex,row:json.array});
											        	//console.info(json.array);
											        	//消息提示 
											            showMessage('提示',json.desc);
											        }else{
											        	
											        	showMessage('提示',json.desc);
											        }  
											        //关闭弹出窗
										        	editRoleDialog.dialog('close');
											    }    
											}); 
									}
								} ],
								onLoad:function(){
									//加载编辑窗口的时候，给表单赋值
									$.post('${pageContext.request.contextPath}/role/findRoleById',{id:rows[0].id},function(data){
										var json=$.parseJSON(data); 
										if(json.status==0){
											
											$('#editRoleForm').form('load',json.array);
											//为树形下拉框赋值
											var vv = json.array.resourceIds;
											//console.info(vv);
											var ids_arr = vv.split(',');
											//console.info(ids_arr);
											$('#setResource').combotree('setValues',ids_arr);
											
										}
									});
								},
								onClose:function(){
									//在窗口关闭之后触发
									editRoleDialog.dialog('destroy');
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
				var rows = roledg.datagrid('getChecked');
				var ids = [];
				if (rows.length > 0) {
					$.messager.confirm('请确认', '确定要删除勾选记录吗？', function(r) {
						if (r) {
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$.ajax({
								url : '${pageContext.request.contextPath}/role/deleteRole',  
								data : {
									ids : ids.join(',')
								},
								dataType : 'json',
								success : function(data) {
									 roledg.datagrid('load');
									 roledg.datagrid('unselectAll');
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