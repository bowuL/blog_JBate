package com.blog.service;

import com.blog.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Still
 * @create: 2020/07/22 10:46
 */
public class LikeService {
    @Autowired
    private JedisService jedisService;

    public int getLikeStatus(int userId, int articleId){
        String likeKey = RedisKeyUtil.getLikeKey(articleId);
        if (jedisService.sismember(likeKey, String.valueOf(userId))){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDislike(articleId);
        return jedisService.sismember(disLikeKey, String.valueOf(userId))?-1:0;

    }

    public long like(int userId, int articleId){
        String likeKey = RedisKeyUtil.getLikeKey(articleId);
        jedisService.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDislike(articleId);
        jedisService.srem(disLikeKey, String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    public long disLike(int userId, int articleId){
        String disLikeKey = RedisKeyUtil.getDislike(articleId);
        jedisService.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(articleId);
        jedisService.srem(likeKey, String.valueOf(userId));

        return jedisService.scard(likeKey);
    }

    public long getLikeCount(int articleId){
        String likeKey = RedisKeyUtil.getLikeKey(articleId);
        return jedisService.scard(likeKey);
    }

    public long getDisLikeCount(int articleId){
        String disLikeKey = RedisKeyUtil.getDislike(articleId);
        return jedisService.scard(disLikeKey);
    }
}
