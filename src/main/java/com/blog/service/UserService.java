package com.blog.service;

import com.blog.dao.LoginTicketDao;
import com.blog.dao.UserDao;
import com.blog.model.LoginTicket;
import com.blog.model.User;
import com.blog.util.JblogUtil;
import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author: Still
 * @create: 2020/07/18 1651
 */
public class UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private LoginTicketDao loginTicketDao;
    public User getUser(int userId) {return userDao.selectById(userId);}
    public User getUserByName(String name) {return userDao.selectByName(name);}
    public void addUser(User user){
        userDao.insertUser(user);
    }


    // 注册
    public Map<String, String> register(String username, String password){
        Map<String, String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isBlank(username)){
            map.put("msg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msg", "密码不能为空");
        }
        User u = userDao.selectByName(username);
        if (u!=null){
            map.put("msg", "用户名已经被占用");
            return map;
        }
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHead_url(String.format("https://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
        user.setPassword(JblogUtil.MD5(password+user.getSalt()));
        user.setRole("user");
        userDao.insertUser(user);

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }


    // 获取loginTicket
    private String addLoginTicket(Integer userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime()+1000*3600*30);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        loginTicketDao.insertLoginTicket(loginTicket);

        return loginTicket.getTicket();
    }

    public Map<String, String> login(String username, String password){
        Map<String, String> map = new HashMap<>();
        Random random = new Random();
        if (StringUtils.isBlank(username)){
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isBlank(password)){
            map.put("msg", "密码不能为空");
            return map;
        }

        User u = userDao.selectByName(username);
        if (u==null){
            map.put("msg", "用户不存在");
            return map;
        }

        if (!JblogUtil.MD5(password+u.getSalt()).equals(u.getPassword())){
            map.put("msg", "密码错误");
            return map;
        }

        String ticket = addLoginTicket(u.getId());
        map.put("ticket", ticket);
        return map;
    }

    public void logout(String ticket) {
        loginTicketDao.updateStatus(ticket, 1);
    }
}
