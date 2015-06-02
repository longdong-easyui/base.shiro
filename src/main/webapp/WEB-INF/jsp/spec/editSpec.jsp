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
    		</tr>
    	</table>
	</div>
	<div data-options="region:'center',title:'center title'" style="padding:5px;background:#eee;">
		   <table id="specDetaildg"  style="width:800px;height:auto"></table> 
	</div> 
 	<script type="text/javascript">
		var specDetaildg;
		var editIndex = undefined;
		var insObj;
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
			        {field:'specId',title:'规格值图片',width:500,formatter:function(value, row, index) {
		        		var html='';
		        		html += '<iframe name="uploadframe" id="uploadframe'+index+'" height="20"';
		        		html += 'frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"';
		        		html += 'src="${pageContext.request.contextPath}/spec/toUploadPage?specId=${spec.id}&id='+row.id+'"></iframe>';
		        		return html;
			        	
					}},  
					{field:'img',title:'图片',width:100,formatter:function(value, row, index) {
						var img = "<img style='height:20;' src='${pageContext.request.contextPath}/spec/findImgById?id="+row.id+"'/>";
		        		return img;
					}},
			        {field:'sortNo',title:'排序',width:50,editor:{type:'numberbox',options:{precision:0}}},
			        {field:'operate',title:'操作',width:300,formatter:function(value, row, index) {
						return "<a href='#' onclick='removeit("+row.id+","+index+")'>删除</a>";
					}}, 
			    ]],
			    toolbar: '#specDetail_toolbar',
			    onSelect:function(rowIndex,rowData){
			    	specDetaildg.datagrid('unselectAll');
			    },
			    onAfterEdit:function(rowIndex, rowData, changes){
			    	console.info('onAfterEdit');
			    	console.info(insObj);
			    	if(insObj!=null){
			    		$.ajax({
							url : '${pageContext.request.contextPath}/spec/updateSpecDetail?id='+insObj.id,  
							data : rowData,
							type:'POST',
							dataType : 'json',
							success : function(data) {
								 if(data.status==0){
								  	showMessage( '提示',data.desc);
								 }else{
								 	showMessage( '提示',data.desc);
								 }
							}
						}); 
			    		editIndex = undefined;
			    		specDetaildg.datagrid('load');
			    	}
				},
			    onClickCell: function(index, field){
			    	console.info(editIndex+':'+ index);
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
		
	     function endEditing(){
	          if (editIndex == undefined){
	        	  return true
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
			 uploadImg(editIndex);
	        
	    }
		/**
		*	调用iframe中的方法
		*/
		function  uploadImg(editIndex){
			var frameid = 'uploadframe'+editIndex;
			console.info(frameid);
			var ifr = document.getElementById(frameid);
	    	var win = ifr.window || ifr.contentWindow;
	    	var fb = win.document.getElementById('fb').value;
	    	console.info(fb);
	    	win.upload(); // 调用iframe中的a函数
		}
		/**
		*	iframe中的回调方法
		*/
		function callback(data){
			
			insObj=data.array;
			 if(insObj!=null){
				if(data.status==0){
					 showMessage( '提示',data.desc);
					 if (editIndex != undefined){
			            	//更新成功后，关闭编辑行
							specDetaildg.datagrid('endEdit', editIndex);
			         } 
				}else{
					 showMessage( '提示',data.desc);
				}
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
	</script>
</body> 

</html>