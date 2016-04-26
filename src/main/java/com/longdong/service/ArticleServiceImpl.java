package com.longdong.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.longdong.dao.ArticleDao;
import com.longdong.entity.Article;


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

	
}
