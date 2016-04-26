package com.longdong.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.longdong.entity.Column;
import com.longdong.service.ColumnService;
import com.longdong.util.EnumUtil;


@Controller
@RequestMapping("/column")
public class ColumnController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ColumnController.class);

	@Autowired
	private ColumnService columnService;
	
	/**
	 * 跳转的列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showColumnlist(Model model) {
		return "column/columnList";
	}
	@RequestMapping("toAddColumnPage")
    public String toAddColumnPage(){
    	return "column/addColumn";
    }
	 @RequestMapping("toEditColumnPage")
	 public String toEditColumnPage(){
	     return "column/editColumn";
	 }
	
	@RequestMapping("findAllColumn")
	public void findAllColumn(Column column, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Column newc = new Column();
			newc.setParentId(-1);
			List<Column> roots = columnService.findAllColumn(newc);
			for(Column root : roots){
				int parentId = root.getId();
				recursiveTree(root,parentId);
			}
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Column.class, "id","parentId","name","url","availableStr","children"); 
	        
			String json = JSON.toJSONString(roots,filter);
			
			logger.info("findAllColumn.response_json:" + json);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("findAllColumn", e);
		}
	}
	/**
	* 递归算法解析成树形结构
	*/
	public void recursiveTree(Column parent,int parentId) {
		
		Column column = new Column();
		column.setParentId(parentId);
		List<Column> childs = columnService.findAllColumn(column);
		parent.setChildren(childs);
		for(Column child : childs){
			recursiveTree(child,child.getId()); 
		}
	}
	
	@RequestMapping("getColumnList")
	public void getColumnList(HttpServletResponse response){
		try {
			Column newc = new Column();
			newc.setParentId(-1);
			List<Column> roots = columnService.findAllColumn(newc);
			for(Column root : roots){
				int parentId = root.getId();
				recursiveTree(root,parentId);
			}
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Column.class, "id","parentId","text","children"); 
	        
			String json = JSON.toJSONString(roots,filter);
			
			logger.info("response json:" + json);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("getColumnList", e);
		}
	}
	
	@RequestMapping("addColumn")
	public void addColumn(Column column,HttpServletRequest request,HttpServletResponse response) {
			
			try{
				Integer parentId = column.getParentId();
				if(parentId==null){
					column.setParentId(-1);
				}
				Column res = columnService.createColumn(column);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("addColumn",e);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
				
			}
			
		}
		
		@RequestMapping("editColumn")
		public void editColumn(Column column, HttpServletResponse response){
			try{
				Column res = columnService.updateColumn(column);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("editColumn",e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
				
			}
		}
		
		@RequestMapping("findColumnById")
		public void findColumnById(Long id,HttpServletResponse response){
			System.out.println(id);
			try{
				Column column = columnService.findOne(id);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array", column);
				writeJson(map,response);
			}catch(Exception e){
				logger.error("findColumnById",e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
			}
		}
		
		@RequestMapping("deleteColumn")
		public void deleteColumn(String ids, HttpServletResponse response){
			logger.info("ids="+ids);
			String[] idArr = ids.split(",");
			List<Column> nodes = new ArrayList<Column>();
			for(String id : idArr){
				Column node = columnService.findOne(Long.valueOf(id));
				recursiveTree(node,node.getId());
				nodes.add(node);
			}
			Set<String> childids = new HashSet<String>();
			for(Column node : nodes){
				String id =node.getId()+"";
				childids.add(id);
				List<Column> children =node.getChildren();
				fetchId(childids,children);
			}
			Iterator<String> itr = childids.iterator();
			
			String temp = StringUtils.join(itr,",");
			try{
				int count = columnService.deleteColumns(temp);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",count);
				writeJson(map,response);
			}catch(Exception e){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
			}
		}
		
		private void fetchId(Set<String> childids, List<Column> children) {
			for(Column child : children){
				String id =child.getId()+"";
				childids.add(id);
				List<Column> childs =child.getChildren();
				fetchId(childids,childs);
			}
		}
		
}













