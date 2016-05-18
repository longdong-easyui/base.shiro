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

import com.longdong.entity.Product;
import com.longdong.service.ProductService;
import com.longdong.util.EnumUtil;

@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductService productService;
	
	/**
	 * 跳转的列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showproductlist(Model model) {
		return "product/productList";
	}
	@RequestMapping("toAddproductPage")
    public String toAddproductPage(){
    	return "product/addproduct";
    }
	 @RequestMapping("toEditproductPage")
	 public String toEditproductPage(){
	     return "product/editproduct";
	 }
	
	@RequestMapping("findAllproduct")
	public void findAllproduct(Product product, HttpServletRequest request,
			HttpServletResponse response) {

		int total = productService.findCount(product);

		List<Product> list = productService.findAllProduct(product);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		writeJson(map, response);
	}
	 @RequestMapping("addproduct")
	public void addproduct(Product product,HttpServletRequest request,HttpServletResponse response) {
			
			try{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
				/**页面控件的文件流**/    
		        MultipartFile multipartFile = multipartRequest.getFile("file");      
		        String logImageName = multipartFile.getOriginalFilename();  
		        System.out.println("logImageName="+logImageName);
		        product.setLogo(multipartFile.getBytes());
				Product res = productService.createProduct(product);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("addproduct",e);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
				
			}
			
		}
		
		@RequestMapping("editproduct")
		public void editproduct(Product product, HttpServletResponse response){
			try{
				Product res = productService.updateProduct(product);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("editproduct",e);
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
				
			}
		}
		
		@RequestMapping("findproductById")
		public void findproductById(Long id,HttpServletResponse response){
			System.out.println(id);
			try{
				Product product = productService.findOne(id);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array", product);
				writeJson(map,response);
			}catch(Exception e){
				logger.error("findproductById",e);
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
				Map<String,InputStream> map= productService.findImgById(id);
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
				logger.error("findImgById",e);
			}
			logger.info("findImgById.end");
		}
		
}













