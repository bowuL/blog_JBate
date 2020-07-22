package com.blog.service;

import com.blog.dao.ArticleTagDao;
import com.blog.dao.TagDao;
import com.blog.model.ArticleTag;
import com.blog.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author: Still
 * @create: 2020/07/22 11:13
 */
public class TagService {
    @Autowired
    private TagDao tagDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    public Tag selectByName(String name){
        return tagDao.selectByname(name);
    }

    public List<Tag> getAllTag(){
        return tagDao.selectAll();
    }

    public List<Tag> getTagByArticleId(int articleId){
        return articleTagDao.selectByArticleId(articleId);
    }

    public int addTag(Tag tag){
        return tagDao.insertTag(tag)>0?tag.getId():0;
    }

    public int addArticleTag(ArticleTag articleTag){
        return articleTagDao.insertArticleTag(articleTag);
    }

    public void updateCount(int tagId, int count){
        tagDao.updateById(count, tagId);
    }
}
