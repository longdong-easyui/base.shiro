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
						<td>姓名</td>
						<td><input type="text" name="username" class="easyui-validatebox"/></td>
						<td>性别</td>
						<td>
							<select class="easyui-combobox" name="sex" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
									<option value="">请选择...</option>
									<option value="0">男</option>
									<option value="1">女</option>
							</select>
						</td>
						<td>生日</td>
						<td>
							<input name="birthday" class="easyui-datebox" />
						</td>
						<td align="right">
							<a id="btn" href="#" onclick="searchFun();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
							<a id="btn" href="#" onclick="clearFun();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">清空</a>
						</td>
					</tr>
				</table>
			</form>
		</div>   
      	<div id="user_toolbar">
      		<table>
      			<tr>
      				<shiro:hasPermission name="user:view">
      						<td>
		      					<a id="btn" href="#" onclick="insertFun();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
		      				</td>
		      				<td>
		      				<div class="datagrid-btn-separator"></div>
		      				</td>
      				</shiro:hasPermission>
      				<shiro:hasPermission name="user:update">
      						<td>
		      					<a id="btn" href="#" onclick="editFun();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
		      				</td>
		      				<td>
		      				<div class="datagrid-btn-separator"></div>
		      				</td>
      				</shiro:hasPermission>
      				<shiro:hasPermission name="user:delete">
      					<td>
	      					<a id="btn" href="#" onclick="deleteFun();" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除</a>
	      				</td>
      				</shiro:hasPermission>
      			</tr>
      		</table>
      	</div>
	    <div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
	    	<table id="userdg"></table>  
	    	
	    </div> 
		<script type="text/javascript">
			var userdg;
			$(function(){
				userdg = $('#userdg').datagrid({ 
					
					fitColumns:true,
					idField:'id', 
					rownumbers : true,
				    url:'${pageContext.request.contextPath}/user/findAllUser',  
				    pagination : true,  
				    sortName : 'id',
					sortOrder : 'desc',
					checkOnSelect:false,
					selectOnCheck:false,
					pageSize : 10,
					pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ], 
				    columns:[[    
				        {field:'id',title:'id',width:100,checkbox:true},    
				        {field:'username',title:'姓名',width:100},
				        {field:'roleIdsName',title:'所属角色',width:200},       
				        {field:'age',title:'年龄',width:50,align:'right',sortable:true,order:'desc'},
				        {field:'mobile',title:'电话',width:100},
				        {field:'sex',title:'性别',width:100,formatter : function(value, row, index) {
																			switch (value) {
																			case 0:
																				return '男';
																			case 1:
																				return '女';
																			}
																		}
						},
				        {field:'address',title:'地址',width:100,formatter:function(value,row,index){
				        													
				        													if(value != undefined){
				        														return '<span title="'+value+'">'+value+'</span>';
				        													}
															        		
															        	}
				        },
				        {field:'email',title:'电子邮件',width:100},
				        {field:'birthday',title:'生日',width:100},
				        {field:'createdDate',title:'创建时间',width:100},
				        {field:'lockedStr',title:'状态',width:100},
				        {field:'action',title:'操作',width:100,formatter:function(value,row,index){
				        													return '<a style="cursor:pointer" onclick="modifyPas();">修改密码</a>';
				        											}
				        }    
				    ]],
				    toolbar: '#user_toolbar'
				        
				});
			});
			function modifyPas(){
				alert('modify password');
			}
			//查询方法
			function searchFun(){
				userdg.datagrid('load',base.serializeObject($('#searchForm')));
			}
			//清空查询表单
			function clearFun(){
				$('#searchForm input').val('');
				userdg.datagrid('load',{});
			}
			
			/**
			 * 新增函数
			 * @returns {} 
			 */
			var addUserDialog;
			function insertFun(){
				addUserDialog =$("<div/>").dialog({
									title: '新增',    
								    width: 500,    
								    height:400,    
								    closed: false,    
								    cache: false,    
								    href: '${pageContext.request.contextPath}/user/toAddUserPage',    
								    modal: true,
								    buttons : [ {
										text : '添加',
										handler : function() {
											$('#addUserForm').form('submit', {   
												url: '${pageContext.request.contextPath}/user/addUser',  
											    success: function(data){ 
											    	var json=$.parseJSON(data);   
											       // console.info(json); 
											        if (json.status==0){
											        	//刷新数据列表,效率较低
											        	//$('#dg').datagrid('reload');
											        	//将新增的数据直接添加到数据列表中
											        	userdg.datagrid('insertRow',{index:0,row:json.array});
											        	
											        	//消息提示 
											            showMessage('提示',json.desc);
											           
											        }else{
											        	showMessage('错误',json.desc);
											        	
											        }
											      	//关闭弹出窗
										        	addUserDialog.dialog('close');
										        	
											    }    
											}); 
										}
									} ],
									onClose:function(){
										//在窗口关闭之后触发
										addUserDialog.dialog('destroy');
									}   
							});
			}
			/**
			 * 编辑函数
			 * @returns {} 
			 */
			var editUserDialog;
			function editFun(){
				//首先判断是否有勾选记录
				var rows = userdg.datagrid('getChecked');
				//获取编辑行的索引值，用于编辑成功后直接更新该行的数据
				var rowindex = userdg.datagrid('getRowIndex',rows[0]);
				if(rows.length==1){
				
					editUserDialog =$("<div/>").dialog({
								title: '编辑',    
							    width: 500,    
							    height:400,    
							    closed: false,    
							    cache: false,    
							    href: '${pageContext.request.contextPath}/user/toEditUserPage',    
							    modal: true,
							    buttons : [ {
									text : '编辑',
									handler : function() {
										$('#editUserForm').form('submit', {   
												url: '${pageContext.request.contextPath}/user/editUser',  
											    success: function(data){ 
											    	var json=$.parseJSON(data); 
											        if (json.status==0){
											        	//刷新数据列表,效率较低
											        	//$('#dg').datagrid('reload');
											        	//将编辑的数据直接更新数据列表中对应的列
											        	userdg.datagrid('updateRow',{index:rowindex,row:json.array});
											        	console.info(json.array);
											        	//消息提示 
											            showMessage('提示',json.desc);
											        }else{
											        	showMessage('提示',json.desc);
											        }    
											      	//关闭弹出窗
										        	editUserDialog.dialog('close');
											    }    
											}); 
									}
								} ],
								onLoad:function(){
									//加载编辑窗口的时候，给表单赋值
									$.post('${pageContext.request.contextPath}/user/findUserById',{id:rows[0].id},function(data){
										var json=$.parseJSON(data); 
										if(json.status==0){
											$('#editUserForm').form('load',json.array);
											//为角色下拉框赋值
											var vv = json.array.roleIds;
											//console.info(vv);
											var ids_arr = vv.split(',');
											//console.info(ids_arr);
											$('#setRole').combobox('setValues',ids_arr);
										}
									});
								},
								onClose:function(){
									//在窗口关闭之后触发
									editUserDialog.dialog('destroy');
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
				var rows = userdg.datagrid('getChecked');
				var ids = [];
				if (rows.length > 0) {
					$.messager.confirm('请确认', '确定要删除勾选记录吗？', function(r) {
						if (r) {
							for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
							}
							$.ajax({
								url : '${pageContext.request.contextPath}/user/deleteUser',  
								data : {
									ids : ids.join(',')
								},
								dataType : 'json',
								success : function(data) {
									 
									 userdg.datagrid('load');
									 userdg.datagrid('unselectAll');
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