package com.zust.EDP.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate redisTemplate;

    public void updateRedis(Integer userId, String id){
        redisTemplate.opsForValue().set(userId+"",id,3600, TimeUnit.SECONDS);
    }
    public void deleteRedis(int userId){
        redisTemplate.delete(userId+"");
    }
    public boolean checkRedis(Integer userId, String id){
        return redisTemplate.opsForValue().get(userId+"").equals(id);
    }

}
