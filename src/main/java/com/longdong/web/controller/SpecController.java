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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.longdong.entity.Spec;
import com.longdong.entity.SpecDetail;
import com.longdong.service.SpecService;
import com.longdong.util.EnumUtil;

@Controller
@RequestMapping("/spec")
public class SpecController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(SpecController.class);

	@Autowired
	private SpecService specService;
	
	/**
	 * 跳转的列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showSpeclist(Model model) {
		return "spec/specList";
	}
	@RequestMapping("toAddSpecPage")
    public String toAddSpecPage(){
    	return "spec/addSpec";
    }
	 @RequestMapping("toEditSpecPage")
	 public String toEditSpecPage(HttpServletRequest request){
		 String id = request.getParameter("id");
		
		 Spec spec = specService.findOne(Long.valueOf(id));
		 request.setAttribute("spec",spec);
		 
	     return "spec/editSpec";
	 }
	 
	@RequestMapping("toUploadPage")
    public String toUploadPage(HttpServletRequest request){
		String specId = request.getParameter("specId");
		request.setAttribute("specId", specId);
		String id = request.getParameter("id");
		if("undefined".equals(id)){
			request.setAttribute("id",null);
		}else{
			request.setAttribute("id", id);
		}
		
    	return "spec/upload";
    }
	@RequestMapping("findAllSpec")
	public void findAllSpec(Spec spec, HttpServletRequest request,
			HttpServletResponse response) {

		int total = specService.findCount(spec);
		List<Spec> list = specService.findAllSpec(spec);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		writeJson(map, response);
	}
	

	
	 @RequestMapping("addSpec")
	public void addspec(Spec spec,HttpServletRequest request,HttpServletResponse response) {
			
			try{
				
				Spec res = specService.createSpec(spec);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("addspec",e);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
				
			}
			
		}
		
		@RequestMapping("editSpec")
		public void editSpec(Spec spec, HttpServletResponse response){
			try{
				Spec res = specService.updateSpec(spec);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("editSpec",e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
				
			}
		}
		
		@RequestMapping("findSpecById")
		public void findSpecById(Long id,HttpServletResponse response){
			System.out.println(id);
			try{
				Spec spec = specService.findOne(id);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array", spec);
				writeJson(map,response);
			}catch(Exception e){
				logger.error("findSpecById",e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
			}
		}
		
		@RequestMapping("findImgById")
		public void findImgById(Long id,HttpServletResponse response){
			logger.info("findImgById.start");
			try {

				Map<String,InputStream> map= specService.findImgById(id);
				InputStream ins = map.get("specImg");
				 //文件流        
				BufferedInputStream bis=new BufferedInputStream(ins);
				//输入缓冲流   
				OutputStream output = response.getOutputStream();
				BufferedOutputStream bos=new BufferedOutputStream(output);
				//输出缓冲流   
				byte data[]=new byte[4096];
				//缓冲字节数   
				int size=0;    
				size=bis.read(data);   
				while (size!=-1){      
				    bos.write(data,0,size);           
				    size=bis.read(data);   
				}   
				bis.close();   
				bos.flush();
				//清空输出缓冲流        
				bos.close();
			} catch (IOException e) {
				logger.error("findImgById", e);
			}
			logger.info("findImgById.end");
		}
		
		@RequestMapping("findAllSpecDetail")
		public void findAllSpecDetail( HttpServletRequest request,HttpServletResponse response){
			String specId = request.getParameter("specId");
			SpecDetail detail = new SpecDetail();
			detail.setSpecId(Long.valueOf(specId));
			
			List<SpecDetail> list = specService.findAllSpecDetail(detail);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("total", 100);
			map.put("rows", list);
			writeJson(map, response);
		}
		
		@RequestMapping("editSpecDetail")
		public void editSpecDetail(SpecDetail specDetail,HttpServletRequest request,HttpServletResponse response){
			try {
				logger.info("editSpecDetail:"+JSON.toJSONString(specDetail));
				
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
				/**页面控件的文件流**/    
		        MultipartFile multipartFile = multipartRequest.getFile("file");
		        byte[] data =multipartFile.getBytes();
		        specDetail.setSpecValue(data);
		        specDetail.setHavImg(1);
		        Map<String,Object> map = new HashMap<String,Object>();
		       
				if(data.length>0){
					 int updateRow = specService.updateImageByDetailId(specDetail);
				     map.put("desc","更新图片成功");
				}else{
					 map.put("desc","无更新图片");
				}
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				writeJson(map,response);
			} catch (Exception e) {
				logger.error("editSpecDetail", e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
			}
		}
		@RequestMapping("updateSpecDetail")
		public void updateSpecDetail(SpecDetail specDetail,HttpServletRequest request,HttpServletResponse response){
			
			try {
				String specId = request.getParameter("specId");
				specDetail.setSpecId(Long.valueOf(specId));
				
				Long id = specDetail.getId();
				SpecDetail sd = specService.findSpecDetailOne(id);
				if(sd==null){
					//不存在
					specDetail.setId(null);
					specDetail.setHavImg(0);  //0:无图片，1：有图片
					SpecDetail newsd = specService.createSpecDetail(specDetail);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
					map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
					map.put("array",newsd);
					writeJson(map,response);
				}else{
					//存在
					int updateRow= specService.updateSpecDetail(specDetail);
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
					map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
					map.put("array",specDetail);
					writeJson(map,response);
				}
				
			} catch (Exception e) {
				logger.error("updateSpecDetail", e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
			}
		}
		
		@RequestMapping("uploadImg")
		public void uploadImg(HttpServletRequest request,HttpServletResponse response){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			writeJson(map,response);
		}
		
		@RequestMapping("deleteSpecDetail")
		public void deleteSpecDetail(HttpServletRequest request,HttpServletResponse response){
			String id = request.getParameter("id");
			boolean res = specService.deleteSpecDetailById(Long.valueOf(id));
			Map<String,Object> map = new HashMap<String,Object>();
			if(res){
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			}else{
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.FAILURE.value);
			}
			
			writeJson(map,response);
		}
}













