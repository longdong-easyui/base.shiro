package com.longdong.dao;

import com.longdong.entity.User;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public User createUser(final User user) {
        final String sql = "insert into sys_user(organization_id, username, password, salt, role_ids, locked,age,sex,mobile,address,email,birthday,createdDate) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setLong(count++, user.getOrganizationId()==null?null:user.getOrganizationId());
                psst.setString(count++, user.getUsername()==null?null:user.getUsername());
                psst.setString(count++, user.getPassword()==null?null:user.getPassword());
                psst.setString(count++, user.getSalt()==null?null:user.getSalt());
                psst.setString(count++, user.getRoleIds()==null?null:user.getRoleIds());
                psst.setInt(count++, user.getLocked()==null?null:user.getLocked());
                psst.setInt(count++,user.getAge()==null?null:user.getAge());
                psst.setInt(count++,user.getSex()==null?null:user.getSex());
                psst.setString(count++,user.getMobile()==null?null:user.getMobile());
                psst.setString(count++,user.getAddress()==null?null:user.getAddress());
                psst.setString(count++,user.getEmail()==null?null:user.getEmail());
                if(user.getBirthday()==null){
                    psst.setDate(count++,null);
                }else{
                	java.sql.Date sqlDate=new java.sql.Date(user.getBirthday().getTime());
                    psst.setDate(count++,sqlDate);
                }
                
                psst.setTimestamp(count++,user.getCreatedDate());
                return psst;
            }
        }, keyHolder);

        user.setId(keyHolder.getKey().longValue());
        return user;
    }

    public User updateUser(User user) {
    	
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append(" update sys_user set updatedDate='"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getBirthday())+"'");
    	
    	if(user.getOrganizationId()!=null){
    		sb.append(",organization_id="+user.getOrganizationId());
    	}
    	if(StringUtils.isNotEmpty(user.getUsername())){
    		sb.append(",username='"+user.getUsername()+"'");
    	}
    	if(StringUtils.isNotEmpty(user.getPassword())){
    		sb.append(",password='"+user.getPassword()+"'");
    	}
    	if(StringUtils.isNotEmpty(user.getSalt())){
    		sb.append(",salt='"+user.getSalt()+"'");
    	}
    	if(StringUtils.isNotEmpty(user.getRoleIds())){
    		sb.append(",role_ids='"+user.getRoleIds()+"'");
    	}
    	if(user.getLocked()!=null){
    		sb.append(",locked="+(user.getLocked()));
    	}
    	if(user.getAge()!=null){
    		sb.append(",age="+user.getAge());
    	}
    	if(user.getSex()!=null){
    		sb.append(",sex="+user.getSex());
    	}
    	if(StringUtils.isNotEmpty(user.getMobile())){
    		sb.append(",mobile='"+user.getMobile()+"'");
    	}
    	if(StringUtils.isNotEmpty(user.getAddress())){
    		sb.append(",address='"+user.getAddress()+"'");
    	}
    	if(StringUtils.isNotEmpty(user.getEmail())){
    		sb.append(",email='"+user.getEmail()+"'");
    	}
    	if(user.getBirthday()!=null){
    		sb.append(",birthday='"+new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday())+"'");
    	}
    	
    	sb.append(" where id="+ user.getId());
       /* String sql = "update sys_user set organization_id=?,username=?, password=?, salt=?, role_ids=?, locked=? ,"
        		+"age=?,sex=?,mobile=?,address=?,email=?,birthday=?,updatedDate=? where id=?";
        jdbcTemplate.update(sql,
                user.getOrganizationId(), user.getUsername(), user.getPassword(), user.getSalt(), user.getRoleIds(), user.getLocked(),
                user.getAge(),user.getSex(),user.getMobile(),user.getAddress(),user.getEmail(),user.getBirthday(),user.getUpdatedDate(),
                user.getId());*/
    	jdbcTemplate.update(sb.toString());
        return user;
    }

    public void deleteUser(Long userId) {
        String sql = "delete from sys_user where id=?";
        jdbcTemplate.update(sql, userId);
    }
    @Override
	public int deleteUsers(String ids) {
    	String sql = "delete from sys_user where id in ("+ids+")";
		return jdbcTemplate.update(sql);
	}
    @Override
    public User findOne(Long userId) {
        String sql = "select id, organization_id as organizationId, username, password, salt, role_ids as roleIds, locked"
        		+" ,age,sex,mobile,address,email,birthday,createdDate "	
        		+" from sys_user where id=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), userId);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public List<User> findAll() {
        String sql = "select id, organization_id as organizationId, username, password, salt, role_ids as roleIds, locked"
        		+" ,age,sex,mobile,address,email,birthday,createdDate "	
        		+ " from sys_user";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
    }


    @Override
    public User findByUsername(String username) {
        String sql = "select id, organization_id as organizationId, username, password, salt, role_ids as roleIds, locked"
        		+" ,age,sex,mobile,address,email,birthday,createdDate "	
        		+ " from sys_user where username=?";
        List<User> userList = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class), username);
        if(userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

	@Override
	public List<User> findAllUser(User user) {
		String wqc = getWhereQueryConditon(user);
		
		String sort = user.getSort();
		String order = user.getOrder();
		int skipResults = user.getSkipResults();
		int rows = user.getRows();
		
		String sql = " select id, organization_id as organizationId, username"
				+" ,password, salt, role_ids as roleIds, locked"
				+" ,age,sex,mobile,address,email,birthday,createdDate"
				+" from sys_user "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		System.out.println("query user sql :"+ sql);	
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
		
	}

	@Override
	public int findCount(User user) {
		String wqc = getWhereQueryConditon(user);
		
		String sql = "select count(*) from sys_user"
				+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
		
	}

	
}
