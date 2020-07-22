package com.blog.controller;

import com.blog.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: Still
 * @create: 2020/07/20 15:54
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

//    @RequestMapping(path = {"/", "/index"})
//    public String index(Model model){
//        List<ViewObject> vos = new ArrayList<>();
//    }

    @RequestMapping("/in")
    public String in(Model model, @RequestParam(value = "next", required = false)String next){
        model.addAttribute("next", next);
        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model, HttpServletResponse httpResponse, @RequestParam String username, @RequestParam String password,
                           @RequestParam(value = "next", required = false)String next){
        Map<String, String> map = userService.register(username, password);
        if (map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            if (StringUtils.isNotBlank(next))
                return "redirect:"+next;
            else
                return "redirect:/";
        }else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletResponse httpResponse,@RequestParam String username, @RequestParam String password,
                        @RequestParam(value = "next", required = false) String next){
        Map<String, String> map = userService.login(username, password);
        if (map.containsKey("ticket")){
            Cookie cookie = new Cookie("ticket", map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            if (StringUtils.isNotBlank(next)){
                return "redirect:" + next;
            }
            return "redirect:/";
        }else {
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(@CookieValue("ticket") String ticket){
        userService.logout(ticket);
        return "redirect:/";
    }
}
