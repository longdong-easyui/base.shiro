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

import com.longdong.entity.Brand;
import com.longdong.service.BrandService;
import com.longdong.util.EnumUtil;

@Controller
@RequestMapping("/brand")
public class BrandController extends BaseController {

	private static final Logger logger = Logger.getLogger(BrandController.class);

	@Autowired
	private BrandService brandService;
	
	/**
	 * 跳转的列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showBrandlist(Model model) {
		return "brand/brandList";
	}
	@RequestMapping("toAddBrandPage")
    public String toAddBrandPage(){
    	return "brand/addBrand";
    }
	 @RequestMapping("toEditBrandPage")
	 public String toEditBrandPage(){
	     return "brand/editBrand";
	 }
	
	@RequestMapping("findAllBrand")
	public void findAllBrand(Brand brand, HttpServletRequest request,
			HttpServletResponse response) {

		int total = brandService.findCount(brand);

		List<Brand> list = brandService.findAllBrand(brand);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		writeJson(map, response);
	}
	 @RequestMapping("addBrand")
	public void addBrand(Brand brand,HttpServletRequest request,HttpServletResponse response) {
			
			try{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
				/**页面控件的文件流**/    
		        MultipartFile multipartFile = multipartRequest.getFile("file");      
		        String logImageName = multipartFile.getOriginalFilename();  
		        System.out.println("logImageName="+logImageName);
		        brand.setLogo(multipartFile.getBytes());
				Brand res = brandService.createBrand(brand);
				
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
		
		@RequestMapping("editBrand")
		public void editBrand(Brand brand, HttpServletResponse response){
			try{
				Brand res = brandService.updateBrand(brand);
				
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
		
		@RequestMapping("findBrandById")
		public void findBrandById(Long id,HttpServletResponse response){
			System.out.println(id);
			try{
				Brand brand = brandService.findOne(id);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array", brand);
				writeJson(map,response);
			}catch(Exception e){
				logger.info(e,e);
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
				Map<String,InputStream> map= brandService.findImgById(id);
				InputStream ins = map.get("logo");
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
				logger.error(e, e);
			}
			logger.info("findImgById.end");
		}
		
}













