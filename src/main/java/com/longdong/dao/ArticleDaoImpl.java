package com.longdong.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import com.longdong.entity.Article;


@Repository
public class ArticleDaoImpl extends BaseDaoImpl implements ArticleDao {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private LobHandler lobHandler;
    
    public Article createArticle(final Article article) {
        final String sql = "insert into article(title,subTitle,type,thumbnail,content,sortNo,isTop,available,createdDate,updatedDate) values(?,?,?,?,?,?,?,?,now(),now())";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                int count = 1;
                psst.setString(count++, article.getTitle());
                psst.setString(count++, article.getSubTitle());
                psst.setString(count++, article.getType());
                psst.setBytes(count++,article.getThumbnail());
                psst.setBytes(count++,article.getContent());
                psst.setInt(count++, article.getSortNo());
                psst.setInt(count++, article.getIsTop());
                psst.setInt(count++, article.getAvailable());
                
                return psst;
            }
        }, keyHolder);
        article.setId(keyHolder.getKey().longValue());
        article.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        return article;
    }

    @Override
    public Article updateArticle(Article article) {
        final String sql = "update article set title=?,subTitle=?,type=?,thumbnail=?,content=?,sortNo=?,isTop=?,available=?,updatedDate=now() where id=?";
        jdbcTemplate.update(
                sql,
                article.getTitle(),article.getSubTitle(),article.getType(),article.getThumbnail(),
                article.getContent(),article.getSortNo(),article.getIsTop(),article.getAvailable(),
                article.getId());
        return article;
    }

    @Override
    public Article findOne(Long articleId) {
        final String sql = "select id,title,subTitle,type,thumbnail,sortNo,isTop,available,createdDate from article where id=?";
       
        @SuppressWarnings("unchecked")
		List<Article> articleList = jdbcTemplate.query(sql,new BeanPropertyRowMapper(Article.class),articleId);
        if(articleList.size() == 0) {
            return null;
        }
        
        return articleList.get(0);
    }

	@Override
	public int findCount(Article article) {
		
		String wqc = getWhereQueryConditon(article);
		
		String sql = "select count(*) from article "+ wqc.toString();
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}
	
	@Override
	public List<Article> findAllArticle(Article article) {
		String wqc = getWhereQueryConditon(article);
		
		String sort = article.getSort();
		String order = article.getOrder();
		int skipResults = article.getSkipResults();
		int rows = article.getRows();
		
		String sql = " select id,title,subTitle,type,thumbnail,sortNo,isTop,available,createdDate from article "
				+ wqc.toString()
				+ " order by "+sort+" "+order+" limit "+skipResults+","+rows;
		
		List<Article> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Article.class));
		
        return list;
		
	}
	
}
