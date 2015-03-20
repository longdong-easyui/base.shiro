package com.longdong.service;

import com.longdong.dao.ResourceDao;
import com.longdong.entity.Resource;

import org.apache.shiro.authz.permission.WildcardPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-14
 * <p>Version: 1.0
 */
@Service

public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    @Override
    public Resource createResource(Resource resource) {
        return resourceDao.createResource(resource);
    }

    @Override
    public Resource updateResource(Resource resource) {
        return resourceDao.updateResource(resource);
    }

    @Override
    public void deleteResource(Long resourceId) {
        resourceDao.deleteResource(resourceId);
    }

    @Override
    public Resource findOne(Long resourceId) {
        return resourceDao.findOne(resourceId);
    }

    @Override
    public List<Resource> findAll() {
        return resourceDao.findAll();
    }

    @Override
    public Set<String> findPermissions(String resourceIds) {
    	
    	List<Resource> resourceList = resourceDao.findResourceByIds(resourceIds);
    	
        Set<String> permissions = new HashSet<String>();
        for(Resource resource : resourceList) {
            if(resource != null && !StringUtils.isEmpty(resource.getPermission())) {
                permissions.add(resource.getPermission());
            }
        }
        return permissions;
    }

    @Override
    public List<Resource> findMenus(Set<String> permissions) {
        List<Resource> allResources = findAll();
        List<Resource> menus = new ArrayList<Resource>();
        for(Resource resource : allResources) {
            if(resource.isRootNode()) {
                continue;
            }
            String type = Resource.ResourceType.menu.name();
            if(!resource.getType().equals(type)) {
                continue;
            }
            if(!hasPermission(permissions, resource)) {
                continue;
            }
            menus.add(resource);
        }
        return menus;
    }

    private boolean hasPermission(Set<String> permissions, Resource resource) {
        if(StringUtils.isEmpty(resource.getPermission())) {
            return true;
        }
        for(String permission : permissions) {
            WildcardPermission p1 = new WildcardPermission(permission);
            WildcardPermission p2 = new WildcardPermission(resource.getPermission());
            if(p1.implies(p2) || p2.implies(p1)) {
                return true;
            }
        }
        return false;
    }

	@Override
	public List<Resource> findResourceByIds(String resourceIds) {
		
		return resourceDao.findResourceByIds(resourceIds);
	}

	@Override
	public List<Resource> findResourceByParentId(Long parentId) {
		
		return resourceDao.findResourceByParentId(parentId);
	}

	@Override
	public int findCount(Resource resource) {
		return resourceDao.findCount(resource);
	}

	@Override
	public List<Resource> findAllResource(Resource resource) {
		
		return resourceDao.findAllResource(resource);
	}

	@Override
	public int deleteResources(String ids) {
		// TODO Auto-generated method stub
		return resourceDao.deleteResources(ids);
	}
}
