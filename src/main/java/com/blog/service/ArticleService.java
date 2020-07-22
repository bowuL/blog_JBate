package com.blog.service;

import com.blog.dao.ArticleDao;
import com.blog.dao.ArticleTagDao;
import com.blog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Still
 * @create: 2020/07/21 14:05
 */
public class ArticleService {
    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    public int addArticle(Article article){
        return articleDao.insertArticle(article)>0?article.getId():0;
    }

    public Article getArticleById(int qId){
        return articleDao.selectById(qId);
    }

    public int getArticleCount(){
        return articleDao.getArticleCount();
    }

    public int getArticleCountByCategory(String category){
        return articleDao.getArticleCountByCategory(category);
    }

    public int getArticleCountByTag(int tagId){
        return articleTagDao.selectArticleCountByTagId(tagId);
    }

    public List<Article> getLatestArticles(int offset, int limit){
        return articleDao.selectLatestArticles(offset, limit);
    }

    public List<Article> getArticleByCategofy(String category, int offset, int limit) {
        return articleDao.selectArticlesByCategory(category, offset, limit);
    }

    public List<Article> getArticleByTag(int tagId, int offset, int limit){
        return articleTagDao.selectByTagId(tagId, offset, limit);
    }

    public void updateCommentCount(int id, int count){
        articleDao.updateArticleCommentCount(id, count);
    }
}
