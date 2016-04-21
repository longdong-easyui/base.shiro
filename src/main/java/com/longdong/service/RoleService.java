package com.longdong.service;

import com.longdong.entity.Role;

import java.util.List;
import java.util.Set;


public interface RoleService {


    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(String roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(String roleIds);
    
	public List<Role> findRoleByIds(String roleIds);
	
	public int findCount(Role role);
	
	public List<Role> findAllRole(Role role);
	public int deleteRoles(String ids);
	
}



















