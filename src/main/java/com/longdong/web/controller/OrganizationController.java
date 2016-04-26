package com.longdong.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSON;
import com.longdong.entity.Organization;
import com.longdong.service.OrganizationService;
import com.longdong.util.EnumUtil;


@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
    @Autowired
    private OrganizationService organizationService;

   /* @RequiresPermissions("organization:view")
    @RequestMapping(method = RequestMethod.GET)
    public String index(Model model) {
        return "organization/index";
    }

    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public String showTree(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        return "organization/tree";
    }*/

    @RequiresPermissions("organization:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
    public String showAppendChildForm(@PathVariable("parentId") Long parentId, Model model) {
        Organization parent = organizationService.findOne(parentId);
        model.addAttribute("parent", parent);
        Organization child = new Organization();
        child.setParentId(parentId);
        child.setParentIds(parent.makeSelfAsParentIds());
        model.addAttribute("child", child);
        model.addAttribute("op", "新增");
        return "organization/appendChild";
    }

    @RequiresPermissions("organization:create")
    @RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
    public String create(Organization organization) {
        organizationService.createOrganization(organization);
        return "redirect:/organization/success";
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}/maintain", method = RequestMethod.GET)
    public String showMaintainForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("organization", organizationService.findOne(id));
        return "organization/maintain";
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Organization organization, RedirectAttributes redirectAttributes) {
        organizationService.updateOrganization(organization);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/organization/success";
    }

    @RequiresPermissions("organization:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        organizationService.deleteOrganization(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/organization/success";
    }


    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{sourceId}/move", method = RequestMethod.GET)
    public String showMoveForm(@PathVariable("sourceId") Long sourceId, Model model) {
        Organization source = organizationService.findOne(sourceId);
        model.addAttribute("source", source);
        model.addAttribute("targetList", organizationService.findAllWithExclude(source));
        return "organization/move";
    }

    @RequiresPermissions("organization:update")
    @RequestMapping(value = "/{sourceId}/move", method = RequestMethod.POST)
    public String move(
            @PathVariable("sourceId") Long sourceId,
            @RequestParam("targetId") Long targetId) {
        Organization source = organizationService.findOne(sourceId);
        Organization target = organizationService.findOne(targetId);
        organizationService.move(source, target);
        return "redirect:/organization/success";
    }

    @RequiresPermissions("organization:view")
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success() {
        return "organization/success";
    }

 /********************************************************************************************/
    
   
    
   // @RequiresPermissions("Organization:view")
    @RequestMapping(method = RequestMethod.GET)
    public String showOrganizationList() {
        return "organization/organizationList";
    }
    @RequestMapping("toAddOrganizationPage")
    public String toAddOrganizationPage(){
    	return "organization/addOrganization";
    }
    
    @RequestMapping("addOrganization")
	public void addOrganization(Organization organization,HttpServletResponse response) {
		
		try{
			Long pid = organization.getParentId();
			if(pid==null){
				organization.setParentId(1L);
				organization.setParentIds("0/1/");
			}else{
				organization.setParentIds("0/1/"+pid+"/");
			}
			Organization r = organizationService.createOrganization(organization);
			
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
	@RequestMapping("updateOrganization")
	public void editOrganization(Organization organization,HttpServletResponse response){
		try{
			String astr = organization.getAvailableStr();
			if("禁用".equals(astr)){
				organization.setAvailable(1);
			}else{
				organization.setAvailable(0);
			}
			organizationService.updateOrganization(organization);
			
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
	@RequestMapping("deleteOrganization")
	public void deleteOrganization(String ids, HttpServletResponse response){
		
		logger.info("ids="+ids);
		
		Set<String> childids = new HashSet<String>();
		String[] idArr = ids.split(",");
		for(String id : idArr){
			childids.add(id);
			//查询根目录的id
			List<Organization> children = organizationService.findOrganizationByParentId(0L);
			Long rootid = children.get(0).getId();
			
			Organization r = organizationService.findOne(Long.valueOf(id));
			String pids = r.getParentIds();
			//判断是否是二级目录
			if(r.getParentId().intValue()==rootid){
				List<Organization> items = organizationService.findOrganizationByParentId(r.getId());
				for(Organization item : items){
					childids.add(String.valueOf(item.getId()));
				}
			}
		}
		Iterator<String> itr = childids.iterator();
		
		String temp = StringUtils.join(itr,",");
		try{
			int count = organizationService.deleteOrganizations(temp);
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
	
	
	@RequestMapping("findAllOrganization")
	public void findAllOrganization(Organization organization,HttpServletResponse response) throws IOException {
		
		int total =organizationService.findCount(organization);
		
		//设置一页显示总条数
		organization.setRows(total);
		List<Organization> list = organizationService.findAllOrganization(organization);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",total);
		map.put("rows",list);
		String json = JSON.toJSONString(map);
		
		logger.info(">>>>>>>>>>>>转换后的JSON字符串：" + json);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
		response.getWriter().flush();
		response.getWriter().close();
		
		
	}
	
	@RequestMapping("findOrganizationById")
	public void findOrganizationById(Long id,HttpServletResponse response){
		System.out.println(id);
		try{
			Organization Organization = organizationService.findOne(id);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array", Organization);
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
	@RequestMapping("getParentOrganizationList")
	public void getParentOrganizationList(HttpServletResponse response){
		List<Organization> list = organizationService.findOrganizationByParentId(1L);
		writeJson(list,response);
	}
	
	
    
    
    
    
}
