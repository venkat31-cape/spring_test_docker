package com.venkat_test.venkat.config;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.url}")
    private String redisUrl;

    @Bean
    public RedisClient redisClient() {
        // Create RedisURI from the URL
        RedisURI redisURI = RedisURI.create(redisUrl);

        // Create RedisClient with the RedisURI
        return RedisClient.create(redisURI);
    }

    @Bean
    public StatefulRedisConnection<String, String> redisConnection(RedisClient redisClient) {
        return redisClient.connect();
    }

    @Bean
    public RedisCommands<String, String> redisCommands(StatefulRedisConnection<String, String> connection) {
        return connection.sync();
    }
}
