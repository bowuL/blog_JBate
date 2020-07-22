package com.blog.dao;

import ch.qos.logback.classic.db.names.TableName;
import com.blog.model.User;

import java.util.List;
import org.apache.ibatis.annotations.*;

/**
 * @author: Still
 * @create: 2020/07/11 1340
 *
 * 用户持久层
 */
@Mapper
public interface UserDao {
    String TABLE_NAME = "user";
    String INSERT_FIELDS = "name, password, salt, head_url, role";
    String SELECT_FIELDS = "id," + INSERT_FIELDS;

    /**
     * 查询所有操作
     * @return
     */
    List<User> findAll();

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values （#{name}, #{password}, #{salt}, #{head_url}, #{role}）"})
    public void insertUser(User user);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    public User selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name=#{name}"})
    public User selectByName(String name);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    public void deleteById(int id);

}
