package com.xwb.utils;

import java.util.List;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import redis.clients.jedis.BinaryClient.LIST_POSITION;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**  
 * redis 工具�?  
 * @ClassName: RedisUtil    
 * @author 陈剑�?    
 * @date 2016�?3�?1�? 上午10:02:02    
 * @version  v 1.0    
 */
@Component
@Primary
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;
    
    /**
     * 获取Jedis实例
     *   
     * @return
     */
    public Jedis getJedis() {
        try {
            if (jedisPool != null) {
                Jedis jedis = jedisPool.getResource();
                return jedis;
            }
            else {
                return null;
            }
        }
        catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 释放jedis资源
     * 
     * @param jedis
     */
    public void returnResource(final Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
    
    /**
     * <p>
     * 通过key获取储存在redis中的value
     * </p>
     * <p>
     * 并释放连�?
     * </p>
     * 
     * @param key
     * @return 成功返回value 失败返回null
     */
    public String get(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getJedis();
            value = jedis.get(key);
        }
        finally {
            returnResource(jedis);
        }
        return value;
    }
    
    /**
     * <p>
     * 向redis存入key和value,并释放连接资�?
     * </p>
     * <p>
     * 如果key已经存在 则覆�?
     * </p>
     * 
     * @param key
     * @param value
     * @return 成功 返回OK 失败返回 0
     */
    public String set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.set(key, value);
        }
        finally {
            returnResource(jedis);
        }
    }
    
    /**
     * <p>
     * 删除指定的key,也可以传入一个包含key的数�?
     * </p>
     * 
     * @param keys �?个key 也可以使 string 数组
     * @return 返回删除成功的个�?
     */
    public Long del(String... keys) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.del(keys);
        }
        finally {
            returnResource(jedis);
        }
    }
    
    /**
     * <p>
     * 通过key向指定的value值追加�??
     * </p>
     * 
     * @param key
     * @param str
     * @return 成功返回 添加后value的长�? 失败 返回 添加�? value 的长�? 异常返回0L
     */
    public Long append(String key, String str) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.append(key, str);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 判断key是否存在
     * </p>
     * 
     * @param key
     * @return true OR false
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        }
        finally {
            returnResource(jedis);
        }
    }
    
    /**
     * <p>
     * 设置key value,如果key已经存在则返�?0,nx==> not exist
     * </p>
     * 
     * @param key
     * @param value
     * @return 成功返回1 如果存在 �? 发生异常 返回 0
     */
    public Long setnx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setnx(key, value);
        }
        finally {
            returnResource(jedis);
        }
    }
    
    /**
     * <p>
     * 设置key value并制定这个键值的有效�?
     * </p>
     * 
     * @param key
     * @param value
     * @param seconds 单位:�?
     * @return 成功返回OK 失败和异常返回null
     */
    public String setex(String key, String value, int seconds) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.setex(key, seconds, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key 和offset 从指定的位置�?始将原先value替换
     * </p>
     * <p>
     * 下标�?0�?�?,offset表示从offset下标�?始替�?
     * </p>
     * <p>
     * 如果替换的字符串长度过小则会这样
     * </p>
     * <p>
     * example:
     * </p>
     * <p>
     * value : bigsea@zto.cn
     * </p>
     * <p>
     * str : abc
     * </p>
     * <P>
     * 从下�?7�?始替�? 则结果为
     * </p>
     * <p>
     * RES : bigsea.abc.cn
     * </p>
     * 
     * @param key
     * @param str
     * @param offset 下标位置
     * @return 返回替换�? value 的长�?
     */
    public Long setrange(String key, String str, int offset) {
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.setrange(key, offset, str);
        }
        finally {
            returnResource(jedis);
        }
    }
    
    /**
     * <p>
     * 通过批量的key获取批量的value
     * </p>
     * 
     * @param keys string数组 也可以是�?个key
     * @return 成功返回value的集�?, 失败返回null的集�? ,异常返回�?
     */
    public List<String> mget(String... keys) {
        Jedis jedis = null;
        List<String> values = null;
        try {
            jedis = getJedis();
            values = jedis.mget(keys);
        }
        finally {
            returnResource(jedis);
        }
        return values;
    }
    
    /**
     * <p>
     * 批量的设置key:value,可以�?�?
     * </p>
     * <p>
     * example:
     * </p>
     * <p>
     * obj.mset(new String[]{"key2","value1","key2","value2"})
     * </p>
     * 
     * @param keysvalues
     * @return 成功返回OK 失败 异常 返回 null
     * 
     */
    public String mset(String... keysvalues) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.mset(keysvalues);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 批量的设置key:value,可以�?�?,如果key已经存在则会失败,操作会回�?
     * </p>
     * <p>
     * example:
     * </p>
     * <p>
     * obj.msetnx(new String[]{"key2","value1","key2","value2"})
     * </p>
     * 
     * @param keysvalues
     * @return 成功返回1 失败返回0
     */
    public Long msetnx(String... keysvalues) {
        Jedis jedis = null;
        Long res = 0L;
        try {
            jedis = getJedis();
            res = jedis.msetnx(keysvalues);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 设置key的�??,并返回一个旧�?
     * </p>
     * 
     * @param key
     * @param value
     * @return 旧�?? 如果key不存�? 则返回null
     */
    public String getset(String key, String value) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.getSet(key, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过下标 和key 获取指定下标位置�? value
     * </p>
     * 
     * @param key
     * @param startOffset �?始位�? �?0 �?�? 负数表示从右边开始截�?
     * @param endOffset
     * @return 如果没有返回null
     */
    public String getrange(String key, int startOffset, int endOffset) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.getrange(key, startOffset, endOffset);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key 对value进行加�??+1操作,当value不是int类型时会返回错误,当key不存在是则value�?1
     * </p>
     * 
     * @param key
     * @return 加�?�后的结�?
     */
    public Long incr(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.incr(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key给指定的value加�??,如果key不存�?,则这是value为该�?
     * </p>
     * 
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.incrBy(key, integer);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 对key的�?�做减减操作,如果key不存�?,则设置key�?-1
     * </p>
     * 
     * @param key
     * @return
     */
    public Long decr(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.decr(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 减去指定的�??
     * </p>
     * 
     * @param key
     * @param integer
     * @return
     */
    public Long decrBy(String key, Long integer) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.decrBy(key, integer);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取value值的长度
     * </p>
     * 
     * @param key
     * @return 失败返回null
     */
    public Long serlen(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.strlen(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key给field设置指定的�??,如果key不存�?,则先创建
     * </p>
     * 
     * @param key
     * @param field 字段
     * @param value
     * @return 如果存在返回0 异常返回null
     */
    public Long hset(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.hset(key, field, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key给field设置指定的�??,如果key不存在则先创�?,如果field已经存在,返回0
     * </p>
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hsetnx(String key, String field, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.hsetnx(key, field, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key同时设置 hash的多个field
     * </p>
     * 
     * @param key
     * @param hash
     * @return 返回OK 异常返回null
     */
    public String hmset(String key, Map<String, String> hash) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.hmset(key, hash);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key �? field 获取指定�? value
     * </p>
     * 
     * @param key
     * @param field
     * @return 没有返回null
     */
    public String hget(String key, String field) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.hget(key, field);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key �? fields 获取指定的value 如果没有对应的value则返回null
     * </p>
     * 
     * @param key
     * @param fields 可以�? �?个String 也可以是 String数组
     * @return
     */
    public List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.hmget(key, fields);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key给指定的field的value加上给定的�??
     * </p>
     * 
     * @param key
     * @param field
     * @param value
     * @return
     */
    public Long hincrby(String key, String field, Long value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.hincrBy(key, field, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key和field判断是否有指定的value存在
     * </p>
     * 
     * @param key
     * @param field
     * @return
     */
    public Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean res = false;
        try {
            jedis = getJedis();
            res = jedis.hexists(key, field);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回field的数�?
     * </p>
     * 
     * @param key
     * @return
     */
    public Long hlen(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.hlen(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
        
    }
    
    /**
     * <p>
     * 通过key 删除指定�? field
     * </p>
     * 
     * @param key
     * @param fields 可以�? �?�? field 也可以是 �?个数�?
     * @return
     */
    public Long hdel(String key, String... fields) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.hdel(key, fields);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回�?有的field
     * </p>
     * 
     * @param key
     * @return
     */
    public Set<String> hkeys(String key) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.hkeys(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回�?有和key有关的value
     * </p>
     * 
     * @param key
     * @return
     */
    public List<String> hvals(String key) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.hvals(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取�?有的field和value
     * </p>
     * 
     * @param key
     * @return
     */
    public Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        Map<String, String> res = null;
        try {
            jedis = getJedis();
            res = jedis.hgetAll(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key向list头部添加字符�?
     * </p>
     * 
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long lpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.lpush(key, strs);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key向list尾部添加字符�?
     * </p>
     * 
     * @param key
     * @param strs 可以使一个string 也可以使string数组
     * @return 返回list的value个数
     */
    public Long rpush(String key, String... strs) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.rpush(key, strs);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key在list指定的位置之前或者之�? 添加字符串元�?
     * </p>
     * 
     * @param key
     * @param where LIST_POSITION枚举类型
     * @param pivot list里面的value
     * @param value 添加的value
     * @return
     */
    public Long linsert(String key, LIST_POSITION where, String pivot, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.linsert(key, where, pivot, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key设置list指定下标位置的value
     * </p>
     * <p>
     * 如果下标超过list里面value的个数则报错
     * </p>
     * 
     * @param key
     * @param index �?0�?�?
     * @param value
     * @return 成功返回OK
     */
    public String lset(String key, Long index, String value) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.lset(key, index, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key从对应的list中删除指定的count�? �? value相同的元�?
     * </p>
     * 
     * @param key
     * @param count 当count�?0时删除全�?
     * @param value
     * @return 返回被删除的个数
     */
    public Long lrem(String key, long count, String value) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.lrem(key, count, value);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key保留list中从strat下标�?始到end下标结束的value�?
     * </p>
     * 
     * @param key
     * @param start
     * @param end
     * @return 成功返回OK
     */
    public String ltrim(String key, long start, long end) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.ltrim(key, start, end);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key从list的头部删除一个value,并返回该value
     * </p>
     * 
     * @param key
     * @return
     */
    public String lpop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.lpop(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key从list尾部删除�?个value,并返回该元素
     * </p>
     * 
     * @param key
     * @return
     */
    public String rpop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.rpop(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key从一个list的尾部删除一个value并添加到另一个list的头�?,并返回该value
     * </p>
     * <p>
     * 如果第一个list为空或�?�不存在则返回null
     * </p>
     * 
     * @param srckey
     * @param dstkey
     * @return
     */
    public String rpoplpush(String srckey, String dstkey) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.rpoplpush(srckey, dstkey);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取list中指定下标位置的value
     * </p>
     * 
     * @param key
     * @param index
     * @return 如果没有返回null
     */
    public String lindex(String key, long index) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.lindex(key, index);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回list的长�?
     * </p>
     * 
     * @param key
     * @return
     */
    public Long llen(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.llen(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取list指定下标位置的value
     * </p>
     * <p>
     * 如果start �? 0 end �? -1 则返回全部的list中的value
     * </p>
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        List<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.lrange(key, start, end);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key向指定的set中添加value
     * </p>
     * 
     * @param key
     * @param members 可以是一个String 也可以是�?个String数组
     * @return 添加成功的个�?
     */
    public Long sadd(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.sadd(key, members);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key删除set中对应的value�?
     * </p>
     * 
     * @param key
     * @param members 可以是一个String 也可以是�?个String数组
     * @return 删除的个�?
     */
    public Long srem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.srem(key, members);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key随机删除�?个set中的value并返回该�?
     * </p>
     * 
     * @param key
     * @return
     */
    public String spop(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.spop(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取set中的差集
     * </p>
     * <p>
     * 以第�?个set为标�?
     * </p>
     * 
     * @param keys 可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Set<String> sdiff(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.sdiff(keys);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取set中的差集并存入到另一个key�?
     * </p>
     * <p>
     * 以第�?个set为标�?
     * </p>
     * 
     * @param dstkey 差集存入的key
     * @param keys 可以使一个string 则返回set中所有的value 也可以是string数组
     * @return
     */
    public Long sdiffstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.sdiffstore(dstkey, keys);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取指定set中的交集
     * </p>
     * 
     * @param keys 可以使一个string 也可以是�?个string数组
     * @return
     */
    public Set<String> sinter(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.sinter(keys);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取指定set中的交集 并将结果存入新的set�?
     * </p>
     * 
     * @param dstkey
     * @param keys 可以使一个string 也可以是�?个string数组
     * @return
     */
    public Long sinterstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.sinterstore(dstkey, keys);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回�?有set的并�?
     * </p>
     * 
     * @param keys 可以使一个string 也可以是�?个string数组
     * @return
     */
    public Set<String> sunion(String... keys) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.sunion(keys);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回�?有set的并�?,并存入到新的set�?
     * </p>
     * 
     * @param dstkey
     * @param keys 可以使一个string 也可以是�?个string数组
     * @return
     */
    public Long sunionstore(String dstkey, String... keys) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.sunionstore(dstkey, keys);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key将set中的value移除并添加到第二个set�?
     * </p>
     * 
     * @param srckey �?要移除的
     * @param dstkey 添加�?
     * @param member set中的value
     * @return
     */
    public Long smove(String srckey, String dstkey, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.smove(srckey, dstkey, member);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取set中value的个�?
     * </p>
     * 
     * @param key
     * @return
     */
    public Long scard(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.scard(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key判断value是否是set中的元素
     * </p>
     * 
     * @param key
     * @param member
     * @return
     */
    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        Boolean res = null;
        try {
            jedis = getJedis();
            res = jedis.sismember(key, member);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取set中随机的value,不删除元�?
     * </p>
     * 
     * @param key
     * @return
     */
    public String srandmember(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.srandmember(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取set中所有的value
     * </p>
     * 
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.smembers(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key向zset中添加value,score,其中score就是用来排序�?
     * </p>
     * <p>
     * 如果该value已经存在则根据score更新元素
     * </p>
     * 
     * @param key
     * @param scoreMembers
     * @return
     */
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zadd(key, scoreMembers);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key向zset中添加value,score,其中score就是用来排序�?
     * </p>
     * <p>
     * 如果该value已经存在则根据score更新元素
     * </p>
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zadd(key, score, member);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key删除在zset中指定的value
     * </p>
     * 
     * @param key
     * @param members 可以使一个string 也可以是�?个string数组
     * @return
     */
    public Long zrem(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zrem(key, members);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key增加该zset中value的score的�??
     * </p>
     * 
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Double zincrby(String key, double score, String member) {
        Jedis jedis = null;
        Double res = null;
        try {
            jedis = getJedis();
            res = jedis.zincrby(key, score, member);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回zset中value的排�?
     * </p>
     * <p>
     * 下标从小到大排序
     * </p>
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zrank(key, member);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回zset中value的排�?
     * </p>
     * <p>
     * 下标从大到小排序
     * </p>
     * 
     * @param key
     * @param member
     * @return
     */
    public Long zrevrank(String key, String member) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zrevrank(key, member);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key将获取score从start到end中zset的value
     * </p>
     * <p>
     * socre从大到小排序
     * </p>
     * <p>
     * 当start�?0 end�?-1时返回全�?
     * </p>
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.zrevrange(key, start, end);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回指定score内zset中的value
     * </p>
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrangebyscore(String key, String max, String min) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.zrevrangeByScore(key, max, min);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回指定score内zset中的value
     * </p>
     * 
     * @param key
     * @param max
     * @param min
     * @return
     */
    public Set<String> zrangeByScore(String key, double max, double min) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.zrevrangeByScore(key, max, min);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 返回指定区间内zset中value的数�?
     * </p>
     * 
     * @param key
     * @param min
     * @param max
     * @return
     */
    public Long zcount(String key, String min, String max) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zcount(key, min, max);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key返回zset中的value个数
     * </p>
     * 
     * @param key
     * @return
     */
    public Long zcard(String key) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zcard(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key获取zset中value的score�?
     * </p>
     * 
     * @param key
     * @param member
     * @return
     */
    public Double zscore(String key, String member) {
        Jedis jedis = null;
        Double res = null;
        try {
            jedis = getJedis();
            res = jedis.zscore(key, member);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key删除给定区间内的元素
     * </p>
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zremrangeByRank(key, start, end);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key删除指定score内的元素
     * </p>
     * 
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByScore(String key, double start, double end) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.zremrangeByScore(key, start, end);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 返回满足pattern表达式的�?有key
     * </p>
     * <p>
     * keys(*)
     * </p>
     * <p>
     * 返回�?有的key
     * </p>
     * 
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set<String> res = null;
        try {
            jedis = getJedis();
            res = jedis.keys(pattern);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
    
    /**
     * <p>
     * 通过key判断值得类型
     * </p>
     * 
     * @param key
     * @return
     */
    public String type(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis = getJedis();
            res = jedis.type(key);
        }
        finally {
            returnResource(jedis);
        }
        return res;
    }
}
