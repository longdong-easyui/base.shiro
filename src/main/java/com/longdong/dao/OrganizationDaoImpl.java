package com.longdong.dao;

import com.longdong.entity.Organization;
import com.longdong.entity.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>Organization: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Repository
public class OrganizationDaoImpl extends BaseDaoImpl implements OrganizationDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Organization createOrganization(final Organization organization) {
        final String sql = "insert into sys_organization( name, parent_id, parent_ids, available) values(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, organization.getName());
                psst.setLong(count++, organization.getParentId());
                psst.setString(count++, organization.getParentIds());
                psst.setInt(count++, organization.getAvailable());
                return psst;
            }
        }, keyHolder);
        organization.setId(keyHolder.getKey().longValue());
        return organization;
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        final String sql = "update sys_organization set name=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                organization.getName(), organization.getAvailable(), organization.getId());
        return organization;
    }

    public void deleteOrganization(Long organizationId) {
        Organization organization = findOne(organizationId);
        final String deleteSelfSql = "delete from sys_organization where id=?";
        jdbcTemplate.update(deleteSelfSql, organizationId);
        final String deleteDescendantsSql = "delete from sys_organization where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql, organization.makeSelfAsParentIds() + "%");
    }


    @Override
    public Organization findOne(Long organizationId) {
        final String sql = "select id, name, parent_id, parent_ids, available from sys_organization where id=?";
        List<Organization> organizationList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class), organizationId);
        if(organizationList.size() == 0) {
            return null;
        }
        return organizationList.get(0);
    }

    @Override
    public List<Organization> findAll() {
        final String sql = "select id, name, parent_id, parent_ids, available from sys_organization";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class));
    }

    @Override
    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        //TODO 改成not exists 利用索引
        final String sql = "select id, name, parent_id, parent_ids, available from sys_organization where id!=? and parent_ids not like ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class), excludeOraganization.getId(), excludeOraganization.makeSelfAsParentIds() + "%");
    }

    @Override
    public void move(Organization source, Organization target) {
        String moveSourceSql = "update sys_organization set parent_id=?,parent_ids=? where id=?";
        jdbcTemplate.update(moveSourceSql, target.getId(), target.getParentIds(), source.getId());
        String moveSourceDescendantsSql = "update sys_organization set parent_ids=concat(?, substring(parent_ids, length(?))) where parent_ids like ?";
        jdbcTemplate.update(moveSourceDescendantsSql, target.makeSelfAsParentIds(), source.makeSelfAsParentIds(), source.makeSelfAsParentIds() + "%");
    }

	@Override
	public List<Organization> findOrganizationByParentId(long parentId) {
		
		 String sql = " select id, name, parent_id, parent_ids, available from sys_organization"
			 		+"  where parent_id =? "
			 		+" order by concat(parent_ids, id) asc";
		 return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Organization.class),parentId);
	}

	@Override
	public int deleteOrganizations(String ids) {
		
		String sql = "delete from sys_organization where id in ("+ids+")";
	    return jdbcTemplate.update(sql);
	}

	@Override
	public int findCount(Organization organization) {
		
		String wqc = getWhereQueryConditon(organization);
		
		String sql = "select count(*) from sys_resource"
				+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public List<Organization> findAllOrganization(Organization organization) {
		String wqc = getWhereQueryConditon(organization);
		
		String sort = organization.getSort();
		String order = organization.getOrder();
		int skipResults = organization.getSkipResults();
		int rows = organization.getRows();
		
		String sql = " select id, name, parent_id, parent_ids, available from sys_organization "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		//System.out.println("query role sql:"+sql);	
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Organization.class));
		
	}
}
