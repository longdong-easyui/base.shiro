package com.longdong.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.longdong.entity.Article;
import com.longdong.entity.ArticleType;


public interface ArticleService {


    public Article createArticle(Article article);
    public Article updateArticle(Article article);
    Article findOne(Long articleId);
	public int findCount(Article article);
	public List<Article> findAllArticle(Article article);
	public ArticleType createArticleType(ArticleType articleType);
	public List<ArticleType> findAllArticleType();
	public Map<String, InputStream> findThumdById(Long id);
	
	
}
