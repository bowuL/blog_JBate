package com.blog.dao;

import com.blog.model.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author: Still
 * @create: 2020/07/18 1622
 */
@Mapper
public interface LoginTicketDao {
    String TABLE_NAME = "login_ticket";
    String INSERT_FIELDS = "user_id, ticket, expired, status";
    String SELECT_FIELDS2 = "id, ticket, expired, status";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into", TABLE_NAME, "(", INSERT_FIELDS, ") values (#{userId}, #{ticket}, #{expired}, #{status})"})
    public void insertLoginTicket(LoginTicket loginTicket);

    @Select({"select", SELECT_FIELDS2, "from", TABLE_NAME, "where user_id=#{userId}"})
    public LoginTicket selectByUserId(int userId);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}"})
    public LoginTicket selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where ticket=#{ticket}"})
    public LoginTicket selectByTicket(String ticket);

    @Update({"update", TABLE_NAME, "set status=#{status} where ticket=#{ticket}"})
    void updateStatus(@Param("ticket") String ticket, @Param("status") int status);

    @Delete({"delete from", TABLE_NAME, "where id=#{id}"})
    void deleteById(@Param("id") int id);

}
