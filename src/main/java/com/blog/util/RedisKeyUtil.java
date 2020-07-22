package com.blog.util;

/**
 * @author: Still
 * @create: 2020/07/21 10:47
 */
public class RedisKeyUtil {

    public static String getCategoryKey(String category){
        return "CATEGORY:"+category +":COUNT";
    }

    public static String getClickCountKey(String url){
        return "CLICK:"+ url +":COUNT";
    }

    public static String getArticleClickCountKey(String url){
        return "ARTICLE:"+ url +":COUNT";
    }

    public static String getLikeKey(int articleId){
        return "LIKE:"+"ARTICLE:"+String.valueOf(articleId);
    }

    public static String getDislike(int articleId){
        return "DISLIKE:ARTICLE:"+String.valueOf(articleId);
    }

    public static String getEventQueue(){
        return "EVENTQUEUE";
    }
}
