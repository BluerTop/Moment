package top.bluer.moment.common.redis;

import org.springframework.data.redis.core.*;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @program: moment-back
 * @description: Redis工具类
 * @author: bluer
 * @date: 2021-11-26 15:58
 * codes: 扁鹊
 **/
@Component
public class MomentRedis {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @description: 添加缓存
     * @date: 2021/11/27 15:09
     * codes: 扁鹊
     **/
    public void set(CachePackage cachePackage) {
        String key = cachePackage.getKey();
        Object value = cachePackage.getValue();
        Duration expire = cachePackage.getExpire();
        if (expire == null) {
            set(key, value);
        } else {
            setEx(key, value, expire);
        }
    }

    /**
     * @description: 存放 key value 对到 redis。
     * @date: 2021/11/27 15:10
     * codes: 扁鹊
     **/
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * @description: 存放 key value 对到 redis，并将 key 的生存时间设为 seconds (以秒为单位)。
     * 如果 key 已经存在， SETEX 命令将覆写旧值。
     * @date: 2021/11/27 15:11
     * codes: 扁鹊
     **/
    public void setEx(String key, Object value, Duration timeout) {
        redisTemplate.opsForValue().set(key, value, timeout);
    }

    /**
     * @description: 返回 key 所关联的 value 值
     * 如果 key 不存在那么返回特殊值 nil 。
     * @date: 2021/11/27 15:12
     * codes: 扁鹊
     **/
    @Nullable
    public <T> T get(String key) {
        return (T) redisTemplate.opsForValue().get(key);
    }

    /**
     * @description: 删除给定的一个 key，不存在的 key 会被忽略。
     * @date: 2021/11/27 15:12
     * codes: 扁鹊
     **/
    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }
}
