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
    				<a id="btn" href="#" onclick="append();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">增加规格值</a>
    			</td>
    			<td>
    				<a id="btn" href="#" onclick="accept();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>
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
			var priKey = $('#priKey').val();
			console.info('priKey'+priKey);
			var spectype = $('#spec_type').val();
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
			        {field:'specId',title:'规格值图片',width:300,formatter:function(value, row, index) {
			        	
			        	if(spectype==0){
			        		var html ="<input type=file name='file"+row.id+"'/>";
			        		var img = "<img src='${pageContext.request.contextPath}/spec/findImgById?id="+row.id+"'/>";
			        		return html+img;
			        	}
					}},  
			        {field:'sortNo',title:'排序',width:50,editor:{type:'numberbox',options:{precision:1}}},
			        {field:'operate',title:'操作',width:300,formatter:function(value, row, index) {
						return "<a href='#' click='removeit("+row.id+")'>删除</a>";
					}}, 
			    ]],
			    toolbar: '#specDetail_toolbar',
			    onSelect:function(rowIndex,rowData){
			    	specDetaildg.datagrid('unselectAll');
			    },
			    onAfterEdit:function(row,changes){
			    	console.info(row);
					//在用户完成编辑的时候触发。
					if(row.specName !=''){
	            		 //更新到数据库
		                $.ajax({
							url : '${pageContext.request.contextPath}/spec/insertSpecDetail?specId='+priKey,  
							data : row,
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
	            	}
					editIndex = undefined;
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
	            if (editIndex != undefined){
	            	var row = specDetaildg.datagrid('getSelected');
	            	//更新成功后，关闭编辑行
					specDetaildg.datagrid('endEdit', editIndex);
	            }
	    }
		function removeit(id){
			 	
	            if (editIndex == undefined){return}
	            specDetaildg.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
	            editIndex = undefined;
	     }
	</script>
</body> 

</html>