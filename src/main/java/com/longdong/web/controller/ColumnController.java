package com.longdong.web.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.longdong.entity.Column;
import com.longdong.service.ColumnService;
import com.longdong.util.EnumUtil;

@Controller
@RequestMapping("/column")
public class ColumnController extends BaseController {

	private static final Logger logger = Logger.getLogger(ColumnController.class);

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

		int total = columnService.findCount(column);

		List<Column> list = columnService.findAllColumn(column);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		writeJson(map, response);
	}
	 @RequestMapping("addColumn")
	public void addcolumn(Column column,HttpServletRequest request,HttpServletResponse response) {
			
			try{
				Column res = columnService.createColumn(column);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.info(e,e);
				
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
				logger.info(e,e);
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
				logger.info(e,e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
			}
		}
			
}













