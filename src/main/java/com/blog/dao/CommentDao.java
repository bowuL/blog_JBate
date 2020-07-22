package com.blog.dao;

import ch.qos.logback.classic.db.names.TableName;
import com.blog.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Still
 * @create: 2020/07/21 14:54
 */
public interface CommentDao {
    String TABLE_NAME = "comment";
    String INSERT_FIELDS = "article_id, content, create_date, user_id, status";
    String SELECT_FIELDS = "id,"+INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{articleId},#{content},#{createDate},#{userId},#{status})"})
    int insertComment(Comment comment);

    @Select({"select", SELECT_FIELDS, "from comment where id=#{id}"})
    Comment selectById(int id);

    @Select({"select", SELECT_FIELDS, "from comment order by id desc limit #{offset}, #{limit}"})
    List<Comment> selectLatestComments(@Param("offset") int offset, @Param("limit") int limit);

    @Select({"select", SELECT_FIELDS, "from comment where article_id=#{articleId} order by create_data desc"})
    List<Comment> selectByArticleId(int articleId);

    @Select({"select count(id) from commet where article_id=#{articleId}"})
    int selectCountByArticleId(int articleId);

    @Update({"update comment set status=#{status} where id=#{commentId}"})
    void updateStatusById(@Param("status") int status, @Param("commentId") int commentId);

    @Delete({"delete from comment where id=#{commentId}"})
    void deleteCommentById(int commentId);

}
