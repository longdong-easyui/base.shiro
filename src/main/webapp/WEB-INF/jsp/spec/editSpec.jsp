<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>

<html>
<head>
<meta charset="UTF-8">
<%@ include file="/WEB-INF/jsp/taglib.jsp" %>
</head>


<body class="easyui-layout">   
	<form method="post" id="editSpecForm">
		<input type="hidden" name="id" id="priKey">
		<fieldset>
			<legend>规格基本信息</legend>
			<table class="table" style="width: 100%;">
				<tr>
					<td>名称</td>
					<td><input name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>类型</td>
					<td><select id="spec_type" name="type" class="easyui-combobox" data-options="panelHeight:'auto',editable:false" style="width: 155px;">
							<option value="1">文本</option>
							<option value="0">图片</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input name="remark" class="easyui-validatebox"/></td>
				</tr>
				<tr>
					<td>排序</td>
					<td><input name="sortNo" class="easyui-validatebox"/></td>
				</tr>
			</table>
		</fieldset>
	</form>  
	<div id="specDetail_toolbar">
    	<table>
    		<tr>
  				<td>
    				<a id="btn1" href="#" onclick="append();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增规格值</a>
    			</td>
    			<td>
    				<a id="btn2" href="#" onclick="cancelEdit();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">取消编辑</a>
    			</td>
    			<td>
    				<a id="btn3" href="#" onclick="accept();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
    			</td>
    			<td>
    				<a id="btn4" href="#" onclick="refresh();" class="easyui-linkbutton" data-options="iconCls:'icon-mini-refresh'">刷新</a>
    			</td>
    		</tr>
    	</table>
	</div>
	
	<div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
		   <table id="specDetaildg"  style="width:800px;height:auto"></table> 
	</div> 
 	<script type="text/javascript">
		var specDetaildg;
		var editIndex = undefined;
		
		$(function(){
			specDetaildg = $('#specDetaildg').datagrid({ 
				fitColumns:true,
				idField:'id', 
				rownumbers : true,
			    url:'${pageContext.request.contextPath}/spec/findAllSpecDetail?specId=${spec.id}',  
			    pagination : false,  
			    sortName : 'id',
				sortOrder : 'desc',
				checkOnSelect:false,
				selectOnCheck:false,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50, 100 ], 
			    columns:[[ 
			        
			        {field:'specName',title:'规格值名称',width:100,editor:{type:'validatebox',options:{required:true,validType:'length[0,10]'}}},			        
			        {field:'sortNo',title:'排序',width:50,editor:{type:'numberbox',options:{precision:0}}},
			        {field:'img',title:'图片',width:100,formatter:function(value, row, index) {
						var html="";
						var havImg =row.havImg;
						if(havImg==1){
							html = "<img style='height:20;' src='${pageContext.request.contextPath}/spec/findImgById?id="+row.id+"'/>";
						}
		        		return html;
					}},
			        {field:'specId',title:'specId',width:500,hidden:true},  
			        {field:'operate',title:'操作',width:300,formatter:function(value, row, index) {
			        	var html="<a href='#' onclick='removeit("+row.id+","+index+")'>删除</a>";
			        	if(row.specName != ''){
			        		html += " | <a href='#' onclick='showUpload("+row.id+")'>上传图片</a>";
			        	}       
						return html;
					}}, 
			    ]],
			    toolbar: '#specDetail_toolbar',
			    onSelect:function(rowIndex,rowData){
			    	specDetaildg.datagrid('unselectAll');
			    },
			    onAfterEdit:function(rowIndex, rowData, changes){
			    	console.info('onAfterEdit');
			    	console.info(rowData);
		    		$.ajax({
						url : '${pageContext.request.contextPath}/spec/updateSpecDetail?specId=${spec.id}',  
						data : rowData,
						type:'POST',
						dataType : 'json',
						success : function(data) {
							 if(data.status==0){
							  	showMessage( '提示',data.desc);
							 }else{
							 	showMessage( '提示',data.desc);
							 }
							 console.info('刷新');
							 refresh();
						}
					}); 
		    		editIndex = undefined;
		    		
				},
			    onClickCell: function(index, field){
			    	//console.info(editIndex+':'+ index);
			    	if(field != 'operate'){
			    		 if (editIndex != index){
				                if (endEditing()){
				                	specDetaildg.datagrid('selectRow', index).datagrid('beginEdit', index);
				                    var ed = specDetaildg.datagrid('getEditor', {index:index,field:field});
				                    ($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
				                    editIndex = index;
				                }else{
				                	specDetaildg.datagrid('selectRow', editIndex);
				                }
				            }
			    	}
		        }
			}); 
		});
		function refresh(){
			specDetaildg.datagrid('load',{specId:'${spec.id}'});
		}
		function endEditing(){
            if (editIndex == undefined){return true}
            if (specDetaildg.datagrid('validateRow', editIndex)){
            	specDetaildg.datagrid('endEdit', editIndex);
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }
		 function append(){
			
			 if (endEditing()){
				 specDetaildg.datagrid('appendRow',{specName:''});
				 editIndex = specDetaildg.datagrid('getRows').length-1;
				 specDetaildg.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
			 }
	     }
		 function accept(){
			 
			 if(endEditing()){
				 specDetaildg.datagrid('endEdit', editIndex);
			 }
	    }
		
		function cancelEdit(){
	            if (editIndex == undefined){return}
	            specDetaildg.datagrid('cancelEdit', editIndex);
	            editIndex = undefined;
	     }
		function removeit(id,index){
			
			 if (editIndex == undefined){
				 $.ajax({
						url:'${pageContext.request.contextPath}/spec/deleteSpecDetail?id='+id,  
						type:'POST',
						dataType : 'json',
						success : function(data) {
							 if(data.status==0){
								specDetaildg.datagrid('deleteRow', index);
							  	showMessage( '提示',data.desc);
							 }else{
							 	showMessage( '提示',data.desc);
							 }
						}
				});
			 }else{
				 specDetaildg.datagrid('cancelEdit', index).datagrid('deleteRow', index);
			 }
		}
		var uploadDialog;
		function showUpload(rowId){
			
			uploadDialog = $("<div/>").dialog({
				title: '上传图片',    
			    width: 500,    
			    height:200,    
			    closed: false,    
			    cache: false,    
			    href: '${pageContext.request.contextPath}/spec/toUploadPage?id='+rowId,    
			    modal: true,
			    buttons : [ {
					text : '上传图片',
					handler : function() {
						$('#uploadForm').form('submit', {   
								url: '${pageContext.request.contextPath}/spec/editSpecDetail',  
							    success: function(data){ 
							    	var json=$.parseJSON(data); 
							        if (json.status==0){
							        	refresh();
							        	//消息提示 
							            showMessage('提示',json.desc);
							        }else{
							        	showMessage('提示',json.desc);
							        }  
							        //关闭弹出窗
						        	uploadDialog.dialog('close');
							    }    
							}); 
					}
				}],
				onClose:function(){
					//在窗口关闭之后触发
					uploadDialog.dialog('destroy');
				}
				  
			});
			
		}
		
		/////////////////////////////////////////////////////////////
		
		/**
		*	调用iframe中的方法,方法废弃不用了
		*/
		function  uploadImg(editIndex){
			var frameid = 'uploadframe'+editIndex;
			console.info(frameid);
			var ifr = document.getElementById(frameid);
			console.info(ifr);
	    	var win = ifr.window || ifr.contentWindow;
	    	win.upload(); // 调用iframe中的a函数
		}
		/**
		*	iframe中的回调方法，方法废弃不用了
		*/
		function callback(data){
			console.info('callback');
			console.info(data);
			if(data==null){
				return;
			}
			var insObj=data.array;
			console.info(insObj);
			 if(insObj!=null){
				if(data.status==0){
					 showMessage( '提示',data.desc);
					 
				}else{
					 showMessage( '提示',data.desc);
				}
			}
			
		}
	</script>
</body> 

</html>