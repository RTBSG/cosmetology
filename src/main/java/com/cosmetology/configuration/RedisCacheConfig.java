package com.cosmetology.configuration;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 自定义redis配置类覆盖容器内默认的配置 其他数据库做缓存同样做法
 * 配置序列化为json 默认非json直接改源码 一般通过工具类在转换，
 * 一旦自定义覆盖了原始的配置会导致原始的properties配置信息失效；需要自己读取配置文件的内容设置
 * @EnableConfigurationProperties(需要绑定的类) 把配置文件加载到容器
 */
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class RedisCacheConfig {
/*    @Autowired
    CacheProperties cacheProperties;*/
    /**
     * @param cacheProperties 配置文件绑定
     * @return
     */
    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {
//        获取原始默认配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
//        覆盖默认配置选项 链式调用完成返回新对象 变更key的规则
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()));
//        变更序列化为json 采用jock的序列化方式
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new GenericJackson2JsonRedisSerializer()));
//        读取配置文件中配置
        CacheProperties.Redis redis = cacheProperties.getRedis();
//        设置缓存ttl 默认永不过期
        if (redis.getTimeToLive() != null){
            config =config.entryTtl(redis.getTimeToLive());
        }
//        设置缓存前缀 默认缓存名作为前缀 也就是没有前缀
        if (redis.getKeyPrefix() != null){
            config =config.prefixKeysWith(redis.getKeyPrefix());

        }
//        是够缓存空值 默认缓存null 解决缓存穿透
        if (!redis.isCacheNullValues() ){
            config =config.disableCachingNullValues() ;
        }
//        是够使用缓存前缀 默认使用
        if (!redis.isUseKeyPrefix()){
            config =config.disableKeyPrefix();
        }
      return config;
    }
}