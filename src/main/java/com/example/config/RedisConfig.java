package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
public class RedisConfig {

    @SuppressWarnings("all")
    @Bean("redisTemplate")
    /*@Lazy延迟加载 加上@lazy注解，则bean的实例会在第一次使用的时候被创建*/
    public RedisTemplate redisTemplate(@Lazy RedisConnectionFactory connectionFactory){
        RedisTemplate redis = new RedisTemplate();

        /*定义key的序列号方式：GenericToStringSerializer可以将任何对象泛化为字符串并序列化*/
        GenericToStringSerializer<String> keySerializer=new GenericToStringSerializer<String>(String.class);
        redis.setKeySerializer(keySerializer);
        redis.setHashKeySerializer(keySerializer);

        /* 定义value的序列号方式：GenericJackson2JsonRedisSerializer 序列化object对象为json字符串 但使用时构造函数不用特定的类参考以上序列化,自定义序列化类;*/
        GenericJackson2JsonRedisSerializer valueSerializer=new GenericJackson2JsonRedisSerializer();
        redis.setValueSerializer(valueSerializer);
        redis.setHashValueSerializer(valueSerializer);

        redis.setConnectionFactory(connectionFactory);
        return redis;
    }
}
