package com.longdong.service;

import com.longdong.entity.Organization;

import java.util.List;


public interface OrganizationService {


    public Organization createOrganization(Organization organization);
    public Organization updateOrganization(Organization organization);
    public void deleteOrganization(Long organizationId);

    Organization findOne(Long organizationId);
    List<Organization> findAll();

    Object findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
    
	public List<Organization> findOrganizationByParentId(long l);
	public int deleteOrganizations(String temp);
	public int findCount(Organization organization);
	public List<Organization> findAllOrganization(Organization organization);
	
	
	
	
}
