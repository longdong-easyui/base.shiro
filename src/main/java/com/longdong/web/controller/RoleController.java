package com.longdong.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.longdong.entity.Resource;
import com.longdong.entity.Role;
import com.longdong.service.ResourceService;
import com.longdong.service.RoleService;
import com.longdong.util.EnumUtil;


@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    /*@RequiresPermissions("role:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("roleList", roleService.findAll());
        return "role/list";
    }*/

    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute("role", new Role());
        model.addAttribute("op", "新增");
        return "role/edit";
    }

    @RequiresPermissions("role:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Role role, RedirectAttributes redirectAttributes) {
        roleService.createRole(role);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/role";
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("role", roleService.findOne(id));
        model.addAttribute("op", "修改");
        return "role/edit";
    }

    @RequiresPermissions("role:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(Role role, RedirectAttributes redirectAttributes) {
        roleService.updateRole(role);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/role";
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("role", roleService.findOne(id));
        model.addAttribute("op", "删除");
        return "role/edit";
    }

    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        roleService.deleteRole(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/role";
    }

    private void setCommonData(Model model) {
        model.addAttribute("resourceList", resourceService.findAll());
    }
    /**************************************************************************************************/
    @RequiresPermissions("role:view")
    @RequestMapping(method = RequestMethod.GET)
    public String showRolelist(Model model) {
        return "role/roleList";
    }
   
    @RequestMapping("toAddRolePage")
    public String toAddRolePage(){
    	return "role/addRole";
    }
    @RequestMapping("toEditRolePage")
    public String toEditRolePage(){
    	return "role/editRole";
    }
    
    @RequestMapping("findAllRole")
    public void findAllRole(Role role,HttpServletResponse response){
    	List<Role> list = roleService.findAllRole(role);
		for(Role r : list){
			setResourceIdsName(r);
		}
		int total =roleService.findCount(role);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",total);
		map.put("rows",list);
		writeJson(map,response);
    }
    @RequestMapping("addRole")
	public void addUsers(Role role,HttpServletResponse response) {
		
		try{
			Role res = roleService.createRole(role);
			setResourceIdsName(res);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array",res);
			writeJson(map,response);
			
		}catch(Exception e){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
			
		}
		
	}
	
	@RequestMapping("editRole")
	public void editUsers(Role role, HttpServletResponse response){
		try{
			Role res = roleService.updateRole(role);
			setResourceIdsName(res);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array",res);
			writeJson(map,response);
			
		}catch(Exception e){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
			
		}
	}
	@RequestMapping("deleteRole")
	public void deleteUsers(String ids, HttpServletResponse response){
		logger.info("ids="+ids);
		try{
			int count = roleService.deleteRoles(ids);
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
	@RequestMapping("findRoleById")
	public void findUserById(Long id,HttpServletResponse response){
		System.out.println(id);
		try{
			Role role = roleService.findOne(id);
			
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array", role);
			writeJson(map,response);
		}catch(Exception e){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
		}
	}
	/**
	 * 获取下拉框的树控件数据
	 * @param response
	 */
	@RequestMapping("getResourceComboTree")
	public void getResourceComboTree(HttpServletResponse response){
		List<Resource> list =resourceService.findResourceByParentId(0L);  //获取根节点
		Long rootId = list.get(0).getId();
		
		List<Resource> resourceList = resourceService.findResourceByParentId(rootId);
		
		for(Resource r: resourceList){
			long id = r.getId();
			List<Resource> children = resourceService.findResourceByParentId(id);
			r.setChildren(children);
		}
		writeJson(resourceList,response);
	}
    /**
	 * 用于列表页面的显示
	 * @param res
	 */
	public void setResourceIdsName(Role res){
		String rids = res.getResourceIds();
		String ridName = convertResourceNames(rids);
		res.setResourceIdsName(ridName);
	}
	/**
	 * 将资源id转换成名称
	 * @param rids
	 * @return
	 */
	private String convertResourceNames(String rids) {
		
		List<Resource> list = resourceService.findResourceByIds(rids);
		StringBuilder sb = new StringBuilder();
		for(Resource r : list){
			if(sb.length()==0){
				sb.append(r.getName());
			}else{
				sb.append(",").append(r.getName());
			}
			
		}
		return sb.toString();
	}
	
	
}
