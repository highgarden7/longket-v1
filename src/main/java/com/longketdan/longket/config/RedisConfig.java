package com.longketdan.longket.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.longketdan.longket.config.exception.CustomException;
import com.longketdan.longket.config.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Configuration
//@EnableCaching
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    private final ObjectMapper objectMapper;

    public RedisConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisPort));
        //redisStandaloneConfiguration.setPassword(redisPassword);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setEnableTransactionSupport(true);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * setData
     * @param key
     * @param value
     * @param expiredTime
     */
    public void setData(String key, String value, int expiredTime){
        redisTemplate().opsForValue().set(key, value, expiredTime, TimeUnit.SECONDS);
    }

    public boolean existsKey(String key){
        return Boolean.TRUE.equals(redisTemplate().hasKey(key));
    }


    /**
     * getData
     * @param key
     * @param classType
     * @return
     * @param <T>
     */
    public <T> Optional<T> getData(String key, Class<T> classType) {
        String jsonData = (String) redisTemplate().opsForValue().get(key);

        try {
            if (StringUtils.hasText(jsonData)) {
                return Optional.ofNullable(objectMapper.readValue(jsonData, classType));
            }
            return Optional.empty();
        } catch (JsonProcessingException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * getKeyList
     * wildcard check = ex) userEmail*
     * @param pattern
     * @return
     */
    public List<String> getKeyList(String pattern){
        List<String> result = new ArrayList<>();
        String removeWildCardPattern = pattern.replace("*", "");

        Set<String> setList = redisTemplate().keys(pattern);
        if(setList == null || setList.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        setList.forEach(data -> result.add(data.replace(removeWildCardPattern+":", "")));

        return  result;
    }

    /**
     * getDataList
     * wildcard check = ex) userEmail*
     * @param pattern
     * @param classType
     * @return
     * @param <T>
     */
    public <T> List<T> getDataList(String pattern, Class<T> classType) {
        List<T> result = new ArrayList<>();

        Set<String> setList = redisTemplate().keys(pattern);
        if(setList == null || setList.isEmpty()){
            throw new CustomException(ErrorCode.NOT_FOUND);
        }
        setList.forEach(data -> result.add(getData(data, classType).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND))));

        return result;
    }


    public boolean deleteData(String key){
        return Boolean.TRUE.equals(redisTemplate().delete(key));
    }


    @SuppressWarnings("deprecation")
    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory());
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())) // Value Serializer 변경
                .prefixCacheNameWith("Test:") // Key Prefix로 "Test:"를 앞에 붙여 저장
                .entryTtl(Duration.ofMinutes(30)); // 캐시 수명 30분
        builder.cacheDefaults(configuration);
        return builder.build();
    }

    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory cf) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                //.entryTtl(Duration.ofMinutes(3L))
                ;

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(cf).cacheDefaults(redisCacheConfiguration).build();
    }
}
