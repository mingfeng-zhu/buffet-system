package cn.ffcs.buffet.common.redis.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static cn.ffcs.buffet.common.redis.constant.StaticValue.UNDERLINE;

/**
 * @Description: Redis工具类
 * @Author: mingfeng.zhu@ffcs.cn
 * @Date: 2020/8/31 11:29
 */
@Component
public final class RedisCommands {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // =============================common============================

    /**
     * 根据命名空间生成key
     *
     * @param nameSpace 命名空间
     * @param key       key
     * @return 新key
     */
    private String getKey(String nameSpace, String key) {
        StringBuilder keyName = new StringBuilder();
        keyName.append(nameSpace).append(UNDERLINE).append(key);
        return keyName.toString();
    }

    /**
     * 指定缓存失效时间
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param time      时间(秒)
     * @return
     */
    public Boolean expire(String nameSpace, String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(getKey(nameSpace, key), time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param nameSpace 命名空间
     * @param key       键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String nameSpace, String key) {
        return redisTemplate.getExpire(getKey(nameSpace, key), TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String nameSpace, String key) {
        try {
            return redisTemplate.hasKey(getKey(nameSpace, key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param nameSpace 命名空间
     * @param key       可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String nameSpace, String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(getKey(nameSpace, key[0]));
            } else {
                // 处理key
                for (int i = 0; i < key.length; i++) {
                    key[i] = getKey(nameSpace, key[i]);
                }
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @return 值
     */
    public Object get(String nameSpace, String key) {
        return nameSpace == null || key == null
                ?
                null : redisTemplate.opsForValue().get(getKey(nameSpace, key));
    }

    /**
     * 普通缓存放入
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param value     值
     * @return true成功 false失败
     */
    public Boolean set(String nameSpace, String key, Object value) {
        try {
            redisTemplate.opsForValue().set(getKey(nameSpace, key), value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param value     值
     * @param time      时间(秒) time要大于0 如果time小于等于0 将不设置
     * @return true成功 false 失败
     */
    public Boolean set(String nameSpace, String key, Object value, long time) {
        try {
            String keyName = getKey(nameSpace, key);
            if (time > 0) {
                redisTemplate.opsForValue().set(keyName, value, time, TimeUnit.SECONDS);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param delta     要增加几(大于0)
     * @return
     */
    public Long incr(String nameSpace, String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(getKey(nameSpace, key), delta);
    }

    /**
     * 递减
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param delta     要减少几(小于0)
     * @return
     */
    public Long decr(String nameSpace, String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(getKey(nameSpace, key), -delta);
    }
    // ================================Map=================================

    /**
     * HashMap
     *
     * @param nameSpace 命名空间
     * @param key       键 不能为null
     * @param item      项 不能为null
     * @return 值
     */
    public Object hashMapGet(String nameSpace, String key, String item) {
        if (nameSpace == null || key == null || item == null) {
            return null;
        }
        return redisTemplate.opsForHash().get(getKey(nameSpace, key), item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hashMapGet(String nameSpace, String key) {
        return redisTemplate.opsForHash().entries(getKey(nameSpace, key));
    }

    /**
     * HashMapSet
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param map       对应多个键值
     * @return true 成功 false 失败
     */
    public Boolean hashMapSet(String nameSpace, String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(getKey(nameSpace, key), map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * HashMapSet 并设置时间
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param map       对应多个键值
     * @param time      时间(秒)
     * @return true成功 false失败
     */
    public Boolean hashMapSet(String nameSpace, String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(getKey(nameSpace, key), map);
            if (time > 0) {
                expire(nameSpace, key, time);
            } else {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param item      项
     * @param value     值
     * @return true 成功 false失败
     */
    public Boolean hashSet(String nameSpace, String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(getKey(nameSpace, key), item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param item      项
     * @param value     值
     * @param time      时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hashSet(String nameSpace, String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(getKey(nameSpace, key), item, value);
            if (time > 0) {
                expire(nameSpace, key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param nameSpace 命名空间
     * @param key       键 不能为null
     * @param item      项 可以使多个 不能为null
     */
    public void hashDel(String nameSpace, String key, Object... item) {
        redisTemplate.opsForHash().delete(getKey(nameSpace, key), item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param nameSpace 命名空间
     * @param key       键 不能为null
     * @param item      项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hasHashKey(String nameSpace, String key, String item) {
        return redisTemplate.opsForHash().hasKey(getKey(nameSpace, key), item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param item      项
     * @param by        要增加几(大于0)
     * @return
     */
    public double hashIncr(String nameSpace, String key, String item, double by) {
        return redisTemplate.opsForHash().increment(getKey(nameSpace, key), item, by);
    }

    /**
     * hash递减
     *
     * @param nameSpace 命名空间
     * @param key       键
     * @param item      项
     * @param by        要减少记(小于0)
     * @return
     */
    public double hashDecr(String nameSpace, String key, String item, double by) {
        return redisTemplate.opsForHash().increment(getKey(nameSpace, key), item, -by);
    }
}