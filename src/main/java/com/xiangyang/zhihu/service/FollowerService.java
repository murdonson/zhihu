package com.xiangyang.zhihu.service;


import com.xiangyang.zhihu.util.JedisAdapter;
import com.xiangyang.zhihu.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Transaction;

import java.util.*;

@Service
public class FollowerService {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean follow(int userId,int entityId,int entityType)
    {
        Date date=new Date();
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        Transaction tx=jedisAdapter.multi(jedisAdapter.getJedis());
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        List<Object> res= tx.exec();
        return res.size()==2&&(Long)res.get(0)>0&&(Long)res.get(1)>0;
    }


    public boolean unfollow(int userId,int entityId,int entityType)
    {
        Date date=new Date();
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        String followeeKey=RedisKeyUtil.getFolloweeKey(userId,entityType);
        Transaction tx=jedisAdapter.multi(jedisAdapter.getJedis());
        tx.zrem(followeeKey,String.valueOf(entityId));
        tx.zrem(followerKey,String.valueOf(userId));
        List<Object> res= tx.exec();
        return res.size()==2&&(Long)res.get(0)>0&&(Long)res.get(1)>0;
    }

    public Long getFollowerCount(int entityType,int entityId)
    {
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapter.zcard(followerKey);
    }

    public Long getFolloweeCount(int userId,int entityType)
    {
        String followeeKey= RedisKeyUtil.getFolloweeKey(userId,entityType);
        return jedisAdapter.zcard(followeeKey);
    }

    public boolean isFollowed(int userId,int entityId,int entityType)
    {
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        return jedisAdapter.zscore(followerKey,String.valueOf(userId))!=null;
    }

    public List<Integer> getFollowers(int entityId,int entityType)
    {
        String followerKey= RedisKeyUtil.getFollowerKey(entityType,entityId);
        return getIds(jedisAdapter.zrevrange(followerKey,0,20));
    }
    public List<Integer> getFollowees(int userId,int entityType)
    {
        String followeeKey= RedisKeyUtil.getFolloweeKey(userId,entityType);
        return getIds(jedisAdapter.zrevrange(followeeKey,0,20));

    }

    public List<Integer> getIds(Set<String> set)
    {
        List<Integer> ids=new ArrayList<>();
        for(String string:set)
        {
            ids.add(Integer.parseInt(string));
        }
        return ids;
    }


}
