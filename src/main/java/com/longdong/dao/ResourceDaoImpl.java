package com.longdong.dao;

import com.longdong.entity.Resource;
import com.longdong.entity.Role;

import org.apache.commons.lang.StringUtils;
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
 * <p>Resource: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Repository
public class ResourceDaoImpl extends BaseDaoImpl implements ResourceDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Resource createResource(final Resource resource) {
        final String sql = "insert into sys_resource(name, type, url, permission, parent_id, parent_ids, available) values(?,?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, resource.getName());
                psst.setString(count++, resource.getType());
                psst.setString(count++, resource.getUrl());
                psst.setString(count++, resource.getPermission());
                psst.setLong(count++, resource.getParentId());
                psst.setString(count++, resource.getParentIds());
                psst.setInt(count++, resource.getAvailable());
                return psst;
            }
        }, keyHolder);
        resource.setId(keyHolder.getKey().longValue());
        return resource;
    }

    @Override
    public Resource updateResource(Resource resource) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("update sys_resource set available="+(resource.getAvailable()));
    	if(StringUtils.isNotBlank(resource.getName())){
    		sb.append(",").append("name='"+resource.getName()+"'");
    	}
    	if(StringUtils.isNotBlank(resource.getType())){
    		sb.append(",").append("type='"+resource.getType()+"'");
    	}
    	if(StringUtils.isNotBlank(resource.getUrl())){
    		sb.append(",").append("url='"+resource.getUrl()+"'");
    	}
    	if(StringUtils.isNotBlank(resource.getPermission())){
    		sb.append(",").append("permission='"+resource.getPermission()+"'");
    	}
    	if(resource.getParentId()!=null){
    		sb.append(",").append("parent_id='"+resource.getParentId()+"'");
    	}
    	if(StringUtils.isNotBlank(resource.getParentIds())){
    		sb.append(",").append("parent_ids='"+resource.getParentIds()+"'");
    	}
    	if(resource.getAvailable()!=null){
    		sb.append(",").append("available='"+resource.getAvailable()+"'");
    	}
    	sb.append(" where id="+ resource.getId());
        /*final String sql = "update sys_resource set name=?, type=?, url=?, permission=?, parent_id=?, parent_ids=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                resource.getName(), resource.getType().name(), resource.getUrl(), resource.getPermission(), resource.getParentId(), resource.getParentIds(), resource.getAvailable(), resource.getId());
        return resource;*/
    	jdbcTemplate.update(sb.toString());
        return resource;
    }

    public void deleteResource(Long resourceId) {
        Resource resource = findOne(resourceId);
        final String deleteSelfSql = "delete from sys_resource where id=?";
        jdbcTemplate.update(deleteSelfSql, resourceId);
        final String deleteDescendantsSql = "delete from sys_resource where parent_ids like ?";
        jdbcTemplate.update(deleteDescendantsSql, resource.makeSelfAsParentIds() + "%");
    }


    @Override
    public Resource findOne(Long resourceId) {
        final String sql = "select  id, name, type, url, permission, parent_id as parentId, parent_ids as parentIds, available from sys_resource where id=?";
        List<Resource> resourceList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Resource.class), resourceId);
        if(resourceList.size() == 0) {
            return null;
        }
        return resourceList.get(0);
    }

    @Override
    public List<Resource> findAll() {
        final String sql = "select id, name, type, url, permission, parent_id as parentId, parent_ids as parentIds, available from sys_resource order by concat(parent_ids, id) asc";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Resource.class));
    }

	@Override
	public List<Resource> findResourceByIds(String resourceIds) {
		 String sql = " select id, name, type, url, permission, parent_id as parentId, parent_ids as parentIds, available"
				 		+" from sys_resource where id in("+resourceIds+") "
				 		+" order by concat(parent_ids, id) asc";
	     return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Resource.class));
		
	}

	@Override
	public List<Resource> findResourceByParentId(Long parentId) {
		
		 String sql = " select id, name, type, url, permission, parent_id as parentId, parent_ids as parentIds, available "
			 		+" from sys_resource where parent_id =? "
			 		+" order by concat(parent_ids, id) asc";
		 return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Resource.class),parentId);
	}

	@Override
	public int findCount(Resource resource) {
		
		String wqc = getWhereQueryConditon(resource);
		
		String sql = "select count(*) from sys_resource"
				+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public List<Resource> findAllResource(Resource resource) {
		String wqc = getWhereQueryConditon(resource);
		
		String sort = resource.getSort();
		String order = resource.getOrder();
		int skipResults = resource.getSkipResults();
		int rows = resource.getRows();
		
		String sql = " select  id, name, type, url, permission, parent_id as parentId, parent_ids as parentIds, available from sys_resource "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		System.out.println("query role sql:"+sql);	
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Resource.class));
		
	}

	@Override
	public int deleteResources(String ids) {
		String sql = "delete from sys_resource where id in ("+ids+")";
	     return jdbcTemplate.update(sql);
	}

}
