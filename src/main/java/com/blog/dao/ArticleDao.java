package com.blog.dao;

import com.blog.model.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Still
 * @create: 2020/07/21 13:39
 */
public interface ArticleDao {
    String TABLE_NAME = "article";
    String INSERT_FIELDS = "title, describes,contet, create_date, comment_count, categofy";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS,")values (#{title},#{describes},#{content},#{createDate},#{commentCount},#{category}"})
    int insertArticle(Article article);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    Article selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "order by id desc limit #{offset}, #{limit}"})
    List<Article> selectLatestArticles(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where category = #{category} order by id desc limit #{offset}, #{limit}"})
    List<Article> selectArticlesByCategory(@Param("category") String category, @Param("offset") int offset, @Param("limit") int limit);

    @Select({"select count(id) from", TABLE_NAME, "where category=#{category}"})
    int getArticleCountByCategory(String category);

    @Select({"select count(id) from", TABLE_NAME})
    int getArticleCount();

    @Update({"update", TABLE_NAME, "set comment_count=#{commentCount} where id=#{sId}"})
    void updateArticleCommentCount(@Param("commentCount") int commentCount, @Param("sId") int sid);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(int id);
}
