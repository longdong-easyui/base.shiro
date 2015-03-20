package com.longdong.service;

import com.longdong.dao.RoleDao;
import com.longdong.entity.Role;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Service

public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceService resourceService;

    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    public Role updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    public void deleteRole(Long roleId) {
        roleDao.deleteRole(roleId);
    }

    @Override
    public Role findOne(Long roleId) {
        return roleDao.findOne(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Set<String> findRoles(String roleIds) {
    	
    	List<Role> roleList = roleDao.findRoleByIds(roleIds);
    	
        Set<String> roles = new HashSet<String>();
        for(Role role : roleList) {
            roles.add(role.getRole());
        }
        return roles;
    }

    @Override
    public Set<String> findPermissions(String roleIds) {
    	List<Role> roleList = roleDao.findRoleByIds(roleIds);
    	
        Set<String> resourceIds = new HashSet<String>();
        
        for(Role role : roleList) {
        	resourceIds.add(role.getResourceIds());
        }
        
        String ids = StringUtils.join(resourceIds.iterator(), ",");
        return resourceService.findPermissions(ids);
    }

	@Override
	public List<Role> findRoleByIds(String roleIds) {
		
		return roleDao.findRoleByIds(roleIds);
	}

	@Override
	public int findCount(Role role) {
		
		return roleDao.findCount(role);
	}

	@Override
	public List<Role> findAllRole(Role role) {
		
		return roleDao.findAllRole(role);
	}

	@Override
	public int deleteRoles(String ids) {
		
		return roleDao.deleteRoles(ids);
	}
}
