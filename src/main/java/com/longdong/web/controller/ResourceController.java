package com.longdong.web.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.longdong.entity.Resource;
import com.longdong.entity.User;
import com.longdong.entity.Resource.ResourceType;
import com.longdong.service.ResourceService;
import com.longdong.service.UserService;
import com.longdong.util.EnumUtil;
import com.longdong.web.bind.annotaion.CurrentUser;


/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ResourceController.class);

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UserService userService;

    @ModelAttribute("types")
    public Resource.ResourceType[] resourceTypes() {
        return Resource.ResourceType.values();
    }

   /* @RequiresPermissions("resource:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("resourceList", resourceService.findAll());
        return "resource/list";
    }*/

    @RequiresPermissions("resource:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
    public String showAppendChildForm(@PathVariable("parentId") Long parentId, Model model) {
        Resource parent = resourceService.findOne(parentId);
        model.addAttribute("parent", parent);
        Resource child = new Resource();
        child.setParentId(parentId);
        child.setParentIds(parent.makeSelfAsParentIds());
        model.addAttribute("resource", child);
        model.addAttribute("op", "新增子节点");
        return "resource/edit";
    }

    @RequiresPermissions("resource:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
    public String create(Resource resource, RedirectAttributes redirectAttributes) {
        resourceService.createResource(resource);
        redirectAttributes.addFlashAttribute("msg", "新增子节点成功");
        return "redirect:/resource";
    }

    @RequiresPermissions("resource:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("resource", resourceService.findOne(id));
        model.addAttribute("op", "修改");
        return "resource/edit";
    }

    @RequiresPermissions("resource:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Resource resource, RedirectAttributes redirectAttributes) {
        resourceService.updateResource(resource);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/resource";
    }

    @RequiresPermissions("resource:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        resourceService.deleteResource(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/resource";
    }
    
    
    /********************************************************************************************/
    
    @RequestMapping("findMenuTree")
    public void findMenuTree(@CurrentUser User loginUser,HttpServletResponse response){
    	 Set<String> permissions = userService.findPermissions(loginUser.getUsername());
         List<Resource> menus = resourceService.findMenus(permissions);
         writeJson(menus,response);
    }
    
   // @RequiresPermissions("resource:view")
    @RequestMapping(method = RequestMethod.GET)
    public String showResourceList() {
        return "resource/resourceList";
    }
    @RequestMapping("toAddResourcePage")
    public String toAddResourcePage(){
    	return "resource/addResource";
    }
    
    @RequestMapping("addResource")
	public void addResource(Resource resource,HttpServletResponse response) {
		
		try{
			Long pid = resource.getParentId();
			if(pid==null){
				resource.setParentId(1L);
				resource.setParentIds("0/1/");
			}else{
				resource.setParentIds("0/1/"+pid+"/");
			}
			Resource r = resourceService.createResource(resource);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array",r);
			writeJson(map,response);
			
		}catch(Exception e){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
			
		}
		
	}
	@RequestMapping("updateResource")
	public void editResource(Resource resource,HttpServletResponse response){
		try{
			String astr = resource.getAvailableStr();
			if("禁用".equals(astr)){
				resource.setAvailable(1);
			}else{
				resource.setAvailable(0);
			}
			resourceService.updateResource(resource);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array",1);
			writeJson(map,response);
			
		}catch(Exception e){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
			
		}
	}
	@RequestMapping("deleteResource")
	public void deleteResource(String ids, HttpServletResponse response){
		
		logger.info("ids="+ids);
		
		Set<String> childids = new HashSet<String>();
		String[] idArr = ids.split(",");
		for(String id : idArr){
			childids.add(id);
			//查询根目录的id
			List<Resource> children = resourceService.findResourceByParentId(0L);
			Long rootid = children.get(0).getId();
			
			Resource r = resourceService.findOne(Long.valueOf(id));
			String pids = r.getParentIds();
			//判断是否是二级目录
			if(r.getParentId().intValue()==rootid){
				List<Resource> items = resourceService.findResourceByParentId(r.getId());
				for(Resource item : items){
					childids.add(String.valueOf(item.getId()));
				}
			}
		}
		Iterator<String> itr = childids.iterator();
		
		String temp = StringUtils.join(itr,",");
		try{
			int count = resourceService.deleteResources(temp);
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
	
	
	@RequestMapping("findAllResource")
	public void findAllResource(Resource resource,HttpServletResponse response) throws IOException {
		
		int total =resourceService.findCount(resource);
		
		//设置一页显示总条数
		resource.setRows(total);
		List<Resource> list = resourceService.findAllResource(resource);
		
		JSONArray arr = new JSONArray();
    	for (int i = 0; i < list.size(); i++) {
    		Resource r= list.get(i);
    		JSONObject obj = r.toJSONObject(r);
    		arr.add(obj);
		}
    	
		JSONObject obj = new JSONObject();
		obj.put("total",total);
		obj.put("rows",arr);
		String json = obj.toString();
		
		logger.info(">>>>>>>>>>>>转换后的JSON字符串：" + json);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		
		
	}
	
	@RequestMapping("findResourceById")
	public void findResourceById(Long id,HttpServletResponse response){
		System.out.println(id);
		try{
			Resource resource = resourceService.findOne(id);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array", resource);
			writeJson(map,response);
		}catch(Exception e){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
		}
		
		
	}
	

	/**
	 * 获取二级菜单
	 * @param response
	 */
	@RequestMapping("getParentResourceList")
	public void getParentResourceList(HttpServletResponse response){
		List<Resource> list = resourceService.findResourceByParentId(1L);
		writeJson(list,response);
	}
	
	@RequestMapping("getResourceTypeList")
	public void getResourceTypeList(HttpServletResponse response){
		ResourceType[] rt = ResourceType.values();
		List<ResourceType> list = Arrays.asList(rt);
		JSONArray arr = new JSONArray();
		for(ResourceType type : list){
			JSONObject obj = new JSONObject();
			String name = type.name();
			String info = type.getInfo();
			obj.put("name",name);
			obj.put("value",info);
			arr.add(obj);
		}
		writeJson(arr,response);
	}
    
    
    
    
    
    
    
    
    
    
    
    
    
}
