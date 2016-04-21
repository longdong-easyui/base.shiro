package com.longdong.dao;

import com.longdong.entity.Role;

import java.util.List;


public interface RoleDao {

    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(Long roleId);

    public Role findOne(Long roleId);
    public List<Role> findAll();
	public List<Role> findRoleByIds(String roleIds);
	public int findCount(Role role);
	public List<Role> findAllRole(Role role);
	public int deleteRoles(String ids);
	
}
