package com.test.springjdbc;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.longdong.entity.Column;
import com.longdong.service.ColumnService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mvc.xml","classpath:spring-config.xml"})

public class ColumTest {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
    private ColumnService columnService;

	@Test
	//@Ignore
	public void findAllUser_pagantion() {
		Column newc = new Column();
		newc.setParentId(-1);
		List<Column> roots = columnService.findAllColumn(newc);
		for(Column root : roots){
			int parentId = root.getId();
			recursiveTree(root,parentId);
		}
		
		String json = JSON.toJSONString(roots);
		System.out.println("#################################");
		System.out.println(json);
		
	}
	
	public void recursiveTree(Column parent,int parentId) {
		
		Column column = new Column();
		column.setParentId(parentId);
		List<Column> childs = columnService.findAllColumn(column);
		parent.setChildren(childs);
		for(Column child : childs){
			recursiveTree(child,child.getId()); 
		}
	}
	
}
