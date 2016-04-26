package com.longdong.web.controller;

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

import com.longdong.entity.Article;
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
	 @RequestMapping("toEditArticlePage")
	 public String toEditArticlePage(){
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
	public void addArticle(Article article,HttpServletRequest request,HttpServletResponse response) {
			
			try{
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;     
				/**页面控件的文件流**/    
		        MultipartFile multipartFile = multipartRequest.getFile("file");      
		        String logImageName = multipartFile.getOriginalFilename();  
		        System.out.println("logImageName="+logImageName);
		        article.setThumbnail(multipartFile.getBytes());
				Article res = articleService.createArticle(article);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("addArticle",e);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
				map.put("desc",e.getMessage());
				writeJson(map,response);
				
			}
			
		}
		
		@RequestMapping("editArticle")
		public void editArticle(Article article, HttpServletResponse response){
			try{
				Article res = articleService.updateArticle(article);
				
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
				map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
				map.put("array",res);
				writeJson(map,response);
				
			}catch(Exception e){
				logger.error("editArticle",e);
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
		
}













