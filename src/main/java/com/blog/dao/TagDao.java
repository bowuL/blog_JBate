package com.blog.dao;

import com.blog.model.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author: Still
 * @create: 2020/07/22 11:14
 */
public interface TagDao {
    String TABLE_NAME = "tag";
    String INSERT_FIELDS = "name, count";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME,"(",INSERT_FIELDS,") values (#{name}, #{count})"})
    int insertTag(Tag tag);

    @Select({"select", SELECT_FIELDS,"from tag where name=#{name}"})
    Tag selectByname(String name);

    @Select({"select", SELECT_FIELDS,"from tag"})
    List<Tag> selectAll();

    @Select("select count(id) from tag")
    int getTagCount();

    @Update({"update tag set count=#{count} where id=#{tagId}"})
    void updateById(@Param("count") int count, @Param("tagId") int tagId);

    @Delete({"delete from tag where id=#{id}"})
    void deleteById(int id);
}
