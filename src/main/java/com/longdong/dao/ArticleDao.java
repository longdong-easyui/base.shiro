package com.longdong.dao;

import java.util.List;

import com.longdong.entity.Article;


public interface ArticleDao {

    public Article createArticle(Article article);
    public Article updateArticle(Article article);
   
    Article findOne(Long articleId);
    
	public int findCount(Article article);
	public List<Article> findAllArticle(Article article);
	
}
