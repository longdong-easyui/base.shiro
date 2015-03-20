package com.longdong.service;

import com.longdong.entity.Resource;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface ResourceService {


    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Long resourceId);

    Resource findOne(Long resourceId);
    List<Resource> findAll();

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds
     * @return
     */
    Set<String> findPermissions(String resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<Resource> findMenus(Set<String> permissions);
    
	public List<Resource> findResourceByIds(String resourceIds);
	
	public List<Resource> findResourceByParentId(Long parentId);
	public int findCount(Resource resource);
	public List<Resource> findAllResource(Resource resource);
	public int deleteResources(String ids);
	
}
