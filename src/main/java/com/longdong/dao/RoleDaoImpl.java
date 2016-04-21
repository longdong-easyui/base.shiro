package com.longdong.dao;

import com.longdong.entity.Role;
import com.longdong.entity.User;

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


@Repository
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public Role createRole(final Role role) {
        final String sql = "insert into sys_role(role, description, resource_ids, available) values(?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, role.getRole());
                psst.setString(count++, role.getDescription());
                psst.setString(count++, role.getResourceIds());
                psst.setInt(count++, role.getAvailable());
                return psst;
            }
        }, keyHolder);
        role.setId(keyHolder.getKey().longValue());
        return role;
    }

    @Override
    public Role updateRole(Role role) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("update sys_role set available="+(role.getAvailable()));
    	if(StringUtils.isNotBlank(role.getRole())){
    		sb.append(",").append("role='"+role.getRole()+"'");
    	}
    	if(StringUtils.isNotBlank(role.getDescription())){
    		sb.append(",").append("description='"+role.getDescription()+"'");
    	}
    	if(StringUtils.isNotBlank(role.getResourceIds())){
    		sb.append(",").append("resource_ids='"+role.getResourceIds()+"'");
    	}
    	sb.append(" where id="+ role.getId());
        /*final String sql = "role=?, description=?, resource_ids=?, available=? where id=?";
        jdbcTemplate.update(
                sql,
                role.getRole(), role.getDescription(), role.getResourceIds(), role.getAvailable(), role.getId());
        return role;*/
    	jdbcTemplate.update(sb.toString());
        return role;
    	
    }

    public void deleteRole(Long roleId) {
        final String sql = "delete from sys_role where id=?";
        jdbcTemplate.update(sql, roleId);
    }


    @Override
    public Role findOne(Long roleId) {
        final String sql = "select id, role, description, resource_ids as resourceIds, available from sys_role where id=?";
        List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class), roleId);
        if(roleList.size() == 0) {
            return null;
        }
        return roleList.get(0);
    }

    @Override
    public List<Role> findAll() {
        final String sql = "select id, role, description, resource_ids as resourceIds, available from sys_role";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class));
    }

	@Override
	public List<Role> findRoleByIds(String roleIds) {
	    String sql = "select id, role, description, resource_ids as resourceIds, available from sys_role where id in (?) ";
	    		
	    List<Role> roleList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Role.class), roleIds);
		return roleList;
	}

	@Override
	public int findCount(Role role) {
		
		String wqc = getWhereQueryConditon(role);
		
		String sql = "select count(*) from sys_role"
				+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public List<Role> findAllRole(Role role) {
		String wqc = getWhereQueryConditon(role);
		
		String sort = role.getSort();
		String order = role.getOrder();
		int skipResults = role.getSkipResults();
		int rows = role.getRows();
		
		String sql = " select id, role, description, resource_ids as resourceIds, available from sys_role "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		System.out.println("query role sql:"+sql);	
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(Role.class));
		
		
	}

	@Override
	public int deleteRoles(String ids) {
		 String sql = "delete from sys_role where id in ("+ids+")";
	     return jdbcTemplate.update(sql);
	}

}
