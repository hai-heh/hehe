package com.hehe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * redis工具类
 */
@Component
public class redisUtils {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    /**
     *设置过期时间
     * @param key  redis的key
     * @param time 过期时间
     * @param timeUnit 时间单位
     */
    public void setExprie(String key, long time,TimeUnit timeUnit){
        redisTemplate.expire(key,time, timeUnit);
    }

    /**
     * 获取过期时间
     * @param key  redis的key
     * @param timeUnit 时间单位
     * @return 过期时间
     */
    public long getExprie(String key,TimeUnit timeUnit){
       return redisTemplate.getExpire(key,timeUnit);
    }

    /**
     * 删除key
     * @param key
     * @return
     */
    public  boolean deleteKey(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除key
     * @param list
     */
    public  void deleteBacthKey(List<String> list){
        redisTemplate.delete(list);
    }


    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    /*
    ============================================================================
               String
     */

    /**
     * 获取value
     * @param key
     * @return
     */
    public  Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 将value设置到redis中
     * @param key
     * @param value
     */
    public void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     *
     * @param key
     * @param value
     * @param time 时间
     * @param timeUnit 时间单位
     */
    public  void set(String key,Object value,long time,TimeUnit timeUnit){
        redisTemplate.opsForValue().set(key,value,time,timeUnit);
    }

    /**
     * 自增
     * @param key
     * @param count 增长因子
     */
    public void incr(String key,long count){
        if(count<0){
            throw new  RuntimeException("递增因子要大于0");
        }
        redisTemplate.opsForValue().increment(key,count);
    }


    /**
     * 自减
     * @param key
     * @param count 增长因子
     */
    public void decr(String key,long count){
        if(count<0){
            throw new  RuntimeException("递减因子要大于0");
        }
        redisTemplate.opsForValue().increment(key,-count);
    }

   /*
   =======================================================
                          Map
    */

    /**
     * 获取Map结构的value
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key,item);
    }

    /**
     *将K-V对放入Map结构
     * @param key
     * @param item Map结构的key
     * @param value
     * @return
     */
    public void hset(String key,String item,Object value){
        redisTemplate.opsForHash().put(key,item,value);
    }

    /**
     * 将K-V对放入Map结构,并设置过期时间
     * @param key
     * @param item
     * @param value
     * @param time
     * @param timeUnit
     */
    public  void hset(String key,String item,Object value,long time,TimeUnit timeUnit){
        hset(key,item,value);
        setExprie(key,time,timeUnit);
    }

    /**
     *将map放入redis的Map结构中
     * @param key
     * @param map
     */
    public  void hset(String key, Map map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    /**
     * 删除hash表的值
     * @param key
     * @param item
     * @param value
     */
    public  void hdelete(String key,String item,String value){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 自增hash表的值
     * @param key
     * @param item
     * @param count
     */
    public  void hincr(String key,String item,double count){
        redisTemplate.opsForHash().increment(key,item,count);
    }

    /**
     * 自减hash表的值
     * @param key
     * @param item
     * @param count
     */
    public void hdecr(String key,String item,double count){
        redisTemplate.opsForHash().increment(key,item,-count);
    }


/*
=======================================================
set结构
 */
        /**
         * 根据key获取Set中的所有值
         * @param key 键
         * @return
         */
    public Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


        /**

         * 根据value从一个set中查询,是否存在

         * @param key 键

         * @param value 值

         * @return true 存在 false不存在
         */

    public boolean sHasKey(String key, Object value) {

        try {

            return redisTemplate.opsForSet().isMember(key, value);

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        }

    }


        /**
         * 将数据放入set缓
         * @param key 键
         * @param values 值 可以是多个

         * @return 成功个数
        325
         */
    public long sSet(String key, Object... values) {

        try {

            return redisTemplate.opsForSet().add(key, values);

        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }


        /**
         * 将set数据放入缓存
         * @param key 键
         * @param time 时间(秒)
         * @param values 值 可以是多个
         * @return 成功个数
         */
    public long sSetAndTime(String key, long time, TimeUnit timeUnit,Object... values) {

        try {

            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
            setExprie(key, time,timeUnit);
            return count;
        } catch (Exception e) {

            e.printStackTrace();

            return 0;

        }

    }
        /**
         * 获取set缓存的长度
         * @param key 键
         * @return
         */

    public long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
        /**
         * 移除值为value的
         * @param key 键
         * @param values 值 可以是多个
         * @return 移除的个数
         */

    public long setRemove(String key, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /*
    ===========================
    list
     */

    /**
     *
     * @param key
     * @param index 列表索引
     * @return
     */
    public Object lget(String key,int index){
        return redisTemplate.opsForList().index(key,index);
    }

    /**
     * 将值放入列表中
     * @param key
     * @param value
     */
    public void lset(String key,Object value){
        redisTemplate.opsForList().rightPush(key,value);
    }

    /**
     * 设置过期时间
     * @param key
     * @param value
     * @param timeUnit
     * @param time
     */
    public void lset(String key,Object value,TimeUnit timeUnit,long time){
        lset(key,value);
        setExprie(key,time,timeUnit);
    }

    /**
     * 获取列表最后一个元素
     * @param key
     * @return
     */
    public Object lgetListEndObject(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 列表删除元素
     * @param key
     * @param index
     * @param o
     */
    public void ldelete(String key,long index,Object o){
        redisTemplate.opsForList().remove(key,index,o);
    }

    /**
     * 将列表放入list中
     * @param key
     * @param list
     */
    public void lsetList(String key,List list){
        redisTemplate.opsForList().rightPushAll(key,list);
    }


}
