package com.longdong.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.longdong.entity.Role;
import com.longdong.entity.User;
import com.longdong.service.OrganizationService;
import com.longdong.service.RoleService;
import com.longdong.service.UserService;
import com.longdong.util.EnumUtil;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;

   /* @RequiresPermissions("user:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("userList", userService.findAll());
        return "user/list";
    }*/

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String showCreateForm(Model model) {
        setCommonData(model);
        model.addAttribute("user", new User());
        model.addAttribute("op", "新增");
        return "user/edit";
    }

    @RequiresPermissions("user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(User user, RedirectAttributes redirectAttributes) {
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("msg", "新增成功");
        return "redirect:/user";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改");
        return "user/edit";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String update(User user, RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("msg", "修改成功");
        return "redirect:/user";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Long id, Model model) {
        setCommonData(model);
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "删除");
        return "user/edit";
    }

    @RequiresPermissions("user:delete")
    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/user";
    }


    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.GET)
    public String showChangePasswordForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        model.addAttribute("op", "修改密码");
        return "user/changePassword";
    }

    @RequiresPermissions("user:update")
    @RequestMapping(value = "/{id}/changePassword", method = RequestMethod.POST)
    public String changePassword(@PathVariable("id") Long id, String newPassword, RedirectAttributes redirectAttributes) {
        userService.changePassword(id, newPassword);
        redirectAttributes.addFlashAttribute("msg", "修改密码成功");
        return "redirect:/user";
    }

    private void setCommonData(Model model) {
        model.addAttribute("organizationList", organizationService.findAll());
        model.addAttribute("roleList", roleService.findAll());
    }
    
    
    /********************************************************************************************/
    @RequiresPermissions("user:view")
    @RequestMapping(method = RequestMethod.GET)
    public String showUserList(){
    	return "user/userList";
    }
    @RequiresPermissions("user:create")
    @RequestMapping("toAddUserPage")
    public String toAddUserPage(){
    	return "user/addUser";
    }
    @RequiresPermissions("user:update")
    @RequestMapping("toEditUserPage")
    public String toEditUserPage(){
    	
    	return "user/editUser";
    }
    
    @RequiresPermissions("user:view")
    @RequestMapping("findAllUser")
    public void findAllUser(User user,HttpServletRequest request,HttpServletResponse response){
    	String bir = request.getParameter("birthday");
    	System.out.println(bir);
    	
		List<User> list = userService.findAllUser(user);
		for(User u : list){
			setRoleIdsName(u);
		}
		int total =userService.findCount(user);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total",total);
		map.put("rows",list);
		writeJson(map,response);
    }
    @RequiresPermissions("user:create")
    @RequestMapping("addUser")
    public void createUser(User user,HttpServletResponse response){
    	 try{
 			user.setCreatedDate(new Timestamp(new Date().getTime()));
 			user.setPassword("111111");
 			user.setOrganizationId(1L);
 			User res = userService.createUser(user);
 			setRoleIdsName(res);
 			
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
    @RequiresPermissions("user:update")
    @RequestMapping("editUser")
    public void editUser(User user,HttpServletResponse response){
    	try{
			user.setUpdatedDate(new Timestamp(new Date().getTime()));
			User res = userService.updateUser(user);
			setRoleIdsName(res);
			
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
    @RequiresPermissions("user:view")
    @RequestMapping("findUserById")
    public void findUserById(Long id,HttpServletResponse response){
    	try{
			User user = userService.findOne(id);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.SUCCESS.key);
			map.put("desc",EnumUtil.RETURN_JSON_STATUS.SUCCESS.value);
			map.put("array", user);
			writeJson(map,response);
		}catch(Exception e){
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("status",EnumUtil.RETURN_JSON_STATUS.FAILURE.key);
			map.put("desc",e.getMessage());
			writeJson(map,response);
		}
    }
    @RequiresPermissions("user:delete")
    @RequestMapping("deleteUser")
    public void deleteUser(String ids, HttpServletResponse response){
    	try{
			int count = userService.deleteUsers(ids);
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
    @RequestMapping("getRoleCombobox")
   	public void getRoleCombobox(HttpServletResponse response){
   		Role role = new Role();
   		int total =roleService.findCount(role);
   		//设置一页显示总条数
   		role.setRows(total);
   		List<Role> list = roleService.findAllRole(role);
   		
   		writeJson(list,response);
   	}
    /**
	 * 用于列表页面的显示
	 * @param res
	 */
	public void setRoleIdsName(User user){
		String roleIds = user.getRoleIds();
		
		if(!StringUtils.isEmpty(roleIds)){
			String ridName = convertRoleNames(roleIds);
			user.setRoleIdsName(ridName);
		}
		
	}
	/**
	 * 将角色id转换成名称
	 * @param rids
	 * @return
	 */
	private String convertRoleNames(String roleIds) {
		List<Role> list = roleService.findRoleByIds(roleIds);
		StringBuilder sb = new StringBuilder();
		for(Role r : list){
			if(sb.length()==0){
				sb.append(r.getRole());
			}else{
				sb.append(",").append(r.getRole());
			}
			
		}
		return sb.toString();
	}
    
}
