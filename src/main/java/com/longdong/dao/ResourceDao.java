package com.longdong.dao;

import com.longdong.entity.Resource;

import java.util.List;


public interface ResourceDao {

    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(Long resourceId);

    Resource findOne(Long resourceId);
    List<Resource> findAll();
	public List<Resource> findResourceByIds(String resourceIds);
	public List<Resource> findResourceByParentId(Long parentId);
	public int findCount(Resource resource);
	public List<Resource> findAllResource(Resource resource);
	public int deleteResources(String ids);

}
