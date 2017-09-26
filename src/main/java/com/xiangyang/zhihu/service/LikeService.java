package com.xiangyang.zhihu.service;

import com.xiangyang.zhihu.model.EntityType;
import com.xiangyang.zhihu.util.JedisAdapter;
import com.xiangyang.zhihu.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.export.MetricExportProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;


    public long like(int userId,int entityId,int entityType)
    {
       String likekey=RedisKeyUtil.getLikeKey(entityType,entityId);
       jedisAdapter.sadd(likekey,String.valueOf(userId));
       String dislikey= RedisKeyUtil.getDisLikeKey(entityType,entityId);
       jedisAdapter.srem(dislikey,String.valueOf(userId));
       return jedisAdapter.scard(likekey);
    }


    public long dislike(int userId,int entityId,int entityType)
    {
        String likekey=RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.srem(likekey,String.valueOf(userId));
        String dislikey= RedisKeyUtil.getDisLikeKey(entityType,entityId);
        jedisAdapter.sadd(dislikey,String.valueOf(userId));
        return jedisAdapter.scard(likekey);
    }

    public long getlikeCount(int entityId,int entityType)
    {
        String likekey=RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likekey);
    }

    public int getStatus(int userId,int entityId,int entityType)
    {
        String likekey=RedisKeyUtil.getLikeKey(entityType,entityId);
        if(jedisAdapter.sismember(likekey,String.valueOf(userId)))
        {
            return 1;
        }
        String dislikekety=RedisKeyUtil.getDisLikeKey(entityType,entityId);
        if(jedisAdapter.sismember(dislikekety,String.valueOf(userId)))
        {
            return -1;
        }
        else{
            return 0;
        }
    }



}
