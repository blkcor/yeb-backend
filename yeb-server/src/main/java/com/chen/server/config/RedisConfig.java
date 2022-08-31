package com.chen.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 默认写法 拿来即用
 *
 * @author: blkcor
 * @DATE: 2022/3/26  18:47
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@Configuration
public class RedisConfig {
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        //防止存到redis的数据乱码  我们自定义序列化方式
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //string类型key序列器
        template.setKeySerializer(new StringRedisSerializer());
        //string类型value序列器
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash类型key序列器
        template.setHashKeySerializer(new StringRedisSerializer());
        //Hash类型value序列器
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //redis工厂
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

}
