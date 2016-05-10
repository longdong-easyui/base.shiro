package com.longdong.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longdong.dao.ArticleDao;
import com.longdong.entity.Article;
import com.longdong.entity.ArticleType;


@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Override
    public Article createArticle(Article article) {
        return articleDao.createArticle(article);
    }

    @Override
    public Article updateArticle(Article article) {
        return articleDao.updateArticle(article);
    }


    @Override
    public Article findOne(Long articleId) {
        return articleDao.findOne(articleId);
    }

   
	@Override
	public int findCount(Article article) {
		
		return articleDao.findCount(article);
	}

	@Override
	public List<Article> findAllArticle(Article article) {
		
		return articleDao.findAllArticle(article);
	}

	@Override
	public ArticleType createArticleType(ArticleType articleType) {
		
		return articleDao.createArticleType(articleType);
	}

	@Override
	public List<ArticleType> findAllArticleType() {
		
		return articleDao.findAllArticleType();
	}

	@Override
	public Map<String, InputStream> findThumdById(Long id) {
		
		return articleDao.findThumdById(id);
	}

	@Override
	public int deleteArticles(String ids) {
		
		return articleDao.deleteArticles(ids);
	}

	
}
