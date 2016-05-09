package com.longdong.dao;

import java.util.List;

import com.longdong.entity.Article;
import com.longdong.entity.ArticleType;


public interface ArticleDao {

    public Article createArticle(Article article);
    public Article updateArticle(Article article);
   
    Article findOne(Long articleId);
    
	public int findCount(Article article);
	public List<Article> findAllArticle(Article article);
	public ArticleType createArticleType(ArticleType articleType);
	public List<ArticleType> findAllArticleType();
	
}
