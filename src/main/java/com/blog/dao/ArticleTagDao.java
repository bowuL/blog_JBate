package com.blog.dao;

import com.blog.model.Article;
import com.blog.model.ArticleTag;
import com.blog.model.Tag;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: Still
 * @create: 2020/07/21 14:06
 */
public interface ArticleTagDao {
    String TABLE_NAME = "article_tag";
    String INSERT_FIELDS = "article_id, tag_id";
    String SELECT_FIELDS = "id,"+INSERT_FIELDS;

    String TAG_FIELDS = "id, name, count";
    String ARTICLE_FIELDS = "id, title,describes,content, created_date, comment_count, category";

    @Insert({"insert into", TABLE_NAME,"(",INSERT_FIELDS,")values (#{articleId}, #{tagId}"})
    int insertArticleTag(ArticleTag articleTag);

    @Select({"select", TAG_FIELDS, "from tag where id in (select tag_id from article_tag where article_id=#{articleId}"})
    List<Tag> selectByArticleId(int articleId);

    @Select({"select", ARTICLE_FIELDS, "from article where id in (select article_id from article_tag where tag_id=#{tagId} limit #{offset}, #{limit}"})
    List<Article> selectByTagId(@Param("tagId") int tagId, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from article where id in (select article_id form article_tag where tag_id=#{tagId}"})
    int selectArticleCountByTagId(int tagId);

    @Delete({"delete from article_tag where id=#{id}"})
    void deleteById(int id);
}
