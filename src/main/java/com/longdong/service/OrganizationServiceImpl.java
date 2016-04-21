package com.longdong.service;

import com.longdong.dao.OrganizationDao;
import com.longdong.entity.Organization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    private OrganizationDao organizationDao;

    @Override
    public Organization createOrganization(Organization organization) {
        return organizationDao.createOrganization(organization);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        return organizationDao.updateOrganization(organization);
    }

    @Override
    public void deleteOrganization(Long organizationId) {
        organizationDao.deleteOrganization(organizationId);
    }

    @Override
    public Organization findOne(Long organizationId) {
        return organizationDao.findOne(organizationId);
    }

    @Override
    public List<Organization> findAll() {
        return organizationDao.findAll();
    }

    @Override
    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        return organizationDao.findAllWithExclude(excludeOraganization);
    }

    @Override
    public void move(Organization source, Organization target) {
        organizationDao.move(source, target);
    }

	@Override
	public List<Organization> findOrganizationByParentId(long parentId) {
		
		return organizationDao.findOrganizationByParentId(parentId);
	}

	@Override
	public int deleteOrganizations(String ids) {
		
		return organizationDao.deleteOrganizations(ids);
	}

	@Override
	public int findCount(Organization organization) {
		
		return organizationDao.findCount(organization);
	}

	@Override
	public List<Organization> findAllOrganization(Organization organization) {
		
		return organizationDao.findAllOrganization(organization);
	}
}
