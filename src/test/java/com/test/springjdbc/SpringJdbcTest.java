package com.test.springjdbc;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.longdong.entity.User;
import com.longdong.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mvc.xml","classpath:spring-config.xml"})

public class SpringJdbcTest {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
    private UserService userService;

	@Test
	@Ignore
	public void findAllUser_pagantion() {
		String sql = " select id, organization_id as organizationId, username, password, "
				+" salt, role_ids as roleIdsStr, locked from sys_user"
				//+" order by id desc limit 0,1 ";
				+" order by id desc limit 0,1 ";
		User user = new User();			
		String sort = user.getSort();
		String order = user.getOrder();
		int skipResults = user.getSkipResults();
		int rows = user.getRows();
		
       List<User> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
       System.out.println(">>>>>>>>>>>pagnation query");
	   System.out.println(list.size());
	   for(User u : list){
		   System.out.println(u.getUsername());
	   }
	}
	
	@Test
	@Ignore
	public void findAllUserNoParams() {
		String sql = " select id, organization_id as organizationId, username, password, "
				+"salt, role_ids as roleIdsStr, locked from sys_user";
		
		
       List<User> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper(User.class));
	   System.out.println(list.size());
	   for(User u : list){
		   System.out.println(u.getUsername());
	   }
	}
	@Test
	@Ignore
	public void findCount(){
		String sql="select count(*) from sys_user";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
		System.out.println(">>>>>>>>>>>>>count="+count);
	}
	@Test
	@Ignore
	public void insertUser() throws ParseException{
		User user = new User();
		user.setUsername("项目Leader");
		user.setPassword("123456");
		user.setOrganizationId(1L);
		user.setAge(22);
		user.setSex(0);
		user.setAddress("长宁区");
		user.setEmail("fded@gmail.com");
		user.setRoleIds("1");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
		Date date = (Date) sdf.parse("2000-12-22");
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		user.setBirthday(null);
		
		user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		userService.createUser(user);
		
	}
	@Test
	@Ignore
	public void updateUser(){
		/*Long userId = 8L;
		User user = userService.findOne(userId);
		user.setBirthday(new Date());
		user.setUpdatedDate(new Timestamp(new Date().getTime()));
		userService.updateUser(user);*/
		User user = new User();
		user.setId(8L);
		user.setUsername("项目成员");
		user.setAge(23);
		user.setUpdatedDate(new Timestamp(new Date().getTime()));
		userService.updateUser(user);
	}
	@Test
	@Ignore
	public void updateUser1() throws ParseException{
		Long userId = 8L;
		User user = userService.findOne(userId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse("1984-10-24");
		user.setBirthday(date);
		user.setUpdatedDate(new Timestamp(new Date().getTime()));
		
		String dateStr1 = new SimpleDateFormat("yyyy-MM-dd").format(user.getBirthday());
		String dateStr2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getUpdatedDate());
		
		String sql = "update sys_user set "
        		+"birthday='"+dateStr1+"'"
        		+",updatedDate='"+dateStr2+"'  where id="+user.getId();
        jdbcTemplate.update(sql);
         
	}
}
