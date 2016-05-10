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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.longdong.entity.Article;
import com.longdong.entity.ArticleType;
import com.longdong.service.ArticleService;
import com.longdong.util.EnumUtil;


@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	@Autowired
	private ArticleService articleService;
	
	/**
	 * 跳转的列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showArticlelist(Model model) {
		return "article/articleList";
	}
	@RequestMapping("toAddArticlePage")
    public String toAddArticlePage(){
    	return "article/addArticle";
    }
	
	@RequestMapping("setArticleTypePage")
    public String setArtTypePage(){
    	return "article/articleType";
    }
	 @RequestMapping("toEditArticlePage")
	 public String toEditArticlePage( HttpServletRequest request){
		 String id = request.getParameter("id");
		 if(!StringUtils.isBlank(id)){
			 Article art = articleService.findOne(Long.valueOf(id));
			 request.setAttribute("article",art);
			 System.out.println("content="+art.getContent());
			// System.out.println("content="+art.getContent().toString());
		 }
		 
	     return "article/editArticle";
	 }
	
	@RequestMapping("findAllArticle")
	public void findAllArticle(Article article, HttpServletRequest request,
			HttpServletResponse response) {

		int total = articleService.findCount(article);

		List<Article> list = articleService.findAllArticle(article);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("rows", list);
		writeJson(map, response);
	}
	 @RequestMapping("addArticle")
	public String addArticle(Article article,HttpServletRequest request,RedirectAttributes attr) {
			
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
			/**页面控件的文件流**/    
	        MultipartFile multipartFile = multipartRequest.getFile("file");      
	        String logImageName = multipartFile.getOriginalFilename();  
	        System.out.println("logImageName="+logImageName);
	        article.setThumbnail(multipartFile.getBytes());
			Article res = articleService.createArticle(article);
			
			
			attr.addAttribute("status", 0);
			attr.addAttribute("desc", "添加文章成功");
		}catch(Exception e){
			logger.error("addArticle",e);
			
			attr.addAttribute("status", 1);
			attr.addAttribute("desc", e.getLocalizedMessage());
			
		}
		return "redirect:/article/toAddArticlePage"; 
	}
	 @RequestMapping("addArticleType")
	 public void addArticleType(ArticleType atype,HttpServletRequest request, HttpServletResponse response){
		 Map<String,Object> map = new HashMap<String,Object>();
		 try{
				
				ArticleType res = articleService.createArticleType(atype);
				
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
					
					
			}catch(Exception e){
				logger.error("addArticleType",e);
				
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.FAILURE.value);
			
			}
		 writeJson(map,response);
			
	 }
	 @RequestMapping("findAllArticleType")
	 public void findAllArticleType(ArticleType articleType,HttpServletResponse response){
		 try { 	
		
			List<ArticleType> list = articleService.findAllArticleType();
	
			SimplePropertyPreFilter filter = new SimplePropertyPreFilter(ArticleType.class, "id","name","code"); 
		    String json=JSON.toJSONString(list,filter);  
		 
			logger.info("response json:" + json);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			logger.error("findAllArticleType",e);
		}
	 }
	@RequestMapping("editArticle")
	public String editArticle(Article article,HttpServletRequest request, HttpServletResponse response,RedirectAttributes attr){
		
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
			/**页面控件的文件流**/    
	        MultipartFile multipartFile = multipartRequest.getFile("file"); 
	        
	        if(!multipartFile.isEmpty()){
	        	System.out.println("editArticle.multipartFile:无缩略图");
	        	String logImageName = multipartFile.getOriginalFilename();  
	 	        System.out.println("thumbnail name="+logImageName);
	 	        article.setThumbnail(multipartFile.getBytes());
	        }
	       
			Article res = articleService.updateArticle(article);
			
			attr.addAttribute("status", 0);
			attr.addAttribute("desc", "添加文章成功");
		}catch(Exception e){
			logger.error("addArticle",e);
			
			attr.addAttribute("status", 1);
			attr.addAttribute("desc", e.getLocalizedMessage());
			
		}
		return "redirect:/article/toEditArticlePage?id="+article.getId(); 
	}
	@RequestMapping("deleteArticle")
	public void deleteArticle(String ids, HttpServletResponse response){
		try{
			int count = articleService.deleteArticles(ids);
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
	@RequestMapping("findArticleById")
	public void findArticleById(Long id,HttpServletResponse response){
		System.out.println(id);
		try{
			Article article = articleService.findOne(id);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array", article);
			writeJson(map,response);
		}catch(Exception e){
			logger.error("findArticleById",e);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
		}
	}
	@RequestMapping("findThumdById")
	public void findThumdById(HttpServletRequest request,HttpServletResponse response){
		logger.info("findThumdById.start");
		try {
			String id = request.getParameter("id");
			Map<String,InputStream> map= articleService.findThumdById(Long.valueOf(id));
			InputStream ins = map.get("thumbnail");
			
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
			logger.error("findThumdById", e);
		}
		logger.info("findThumdById.end");
	}	
}













