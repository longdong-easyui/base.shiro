package com.test.springjdbc;

import java.text.ParseException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.longdong.entity.Article;
import com.longdong.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-mvc.xml","classpath:spring-config.xml"})

public class ArticleTest {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Autowired
    private ArticleService articleService;

	@Test
//	@Ignore
	public void insertArticle() throws ParseException{
		Article art = new Article();
		art.setTitle("习近平考察小岗村 重温中国改革历程");
		art.setSubTitle("习近平在网信工作座谈会上讲话  专题  漫评  这三年");
		art.setType("NEW");
		art.setSortNo(1);
		art.setAvailable(1);
		art.setIsTop(0);
	
		articleService.createArticle(art);
		
	}
	
}
