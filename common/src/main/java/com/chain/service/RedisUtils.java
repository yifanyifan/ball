package com.chain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis操作实现类
 * Created by macro on 2020/3/3.
 */
@Component
public class RedisUtils {
    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public RedisUtils(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }


    public static void set(String key, Object value, long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }


    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }


    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }


    public static Boolean del(String key) {
        return redisTemplate.delete(key);
    }


    public static Long del(List<String> keys) {
        return redisTemplate.delete(keys);
    }


    public static Boolean expire(String key, long time) {
        return redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }


    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    public static Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }


    public static Long incr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }


    public static Long decr(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, -delta);
    }


    public static Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }


    public static Boolean hSet(String key, String hashKey, Object value, long time) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }


    public static void hSet(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }


    public static Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }


    public static Boolean hSetAll(String key, Map<String, Object> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }


    public static void hSetAll(String key, Map<String, ?> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }


    public static void hDel(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }


    public static Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }


    public static Long hIncr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }


    public static Long hDecr(String key, String hashKey, Long delta) {
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }


    public static Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }


    public static Long sAdd(String key, Object... values) {
        return redisTemplate.opsForSet().add(key, values);
    }


    public static Long sAdd(String key, long time, Object... values) {
        Long count = redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }


    public static Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }


    public static Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }


    public static Long sRemove(String key, Object... values) {
        return redisTemplate.opsForSet().remove(key, values);
    }


    public static List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }


    public static Long lSize(String key) {
        return redisTemplate.opsForList().size(key);
    }


    public static Object lIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }


    public static Long lPush(String key, Object value) {
        return redisTemplate.opsForList().rightPush(key, value);
    }


    public static Long lPush(String key, Object value, long time) {
        Long index = redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }


    public static Long lPushAll(String key, Object... values) {
        return redisTemplate.opsForList().rightPushAll(key, values);
    }


    public static Long lPushAll(String key, Long time, Object... values) {
        Long count = redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }


    public static Long lRemove(String key, long count, Object value) {
        return redisTemplate.opsForList().remove(key, count, value);
    }
}
