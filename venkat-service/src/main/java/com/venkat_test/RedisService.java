package com.venkat_test;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

    public static void main(String[] args) {
        // Create a Redis client with SSL/TLS enabled
        RedisClient redisClient = RedisClient.create("rediss://red-crc2vk5ds78s73964h00:f9U9qssZSrR2pukTvyNESICMj9Rt6M9r@oregon-redis.render.com:6379");
        StatefulRedisConnection<String, String> connection = redisClient.connect();
        RedisCommands<String, String> syncCommands = connection.sync();

        try {
            // Test connection
            String response = syncCommands.ping();
            System.out.println("Response: " + response); // Should print "PONG"

            // Set and get a key
            syncCommands.set("testkey", "Hello Redis");
            String value = syncCommands.get("testkey");
            System.out.println("Value: " + value); // Should print "Hello Redis"
        } finally {
            // Close connection
            connection.close();
            redisClient.shutdown();
        }
    }


    private final RedisCommands<String, String> redisCommands;

    @Autowired
    public RedisService(RedisCommands<String, String> redisCommands) {
        this.redisCommands = redisCommands;
    }

    public String ping() {
        return redisCommands.ping();
    }

    public void setValue(String key, String value) {
        redisCommands.set(key, value);
    }

    public String getValue(String key) {
        return redisCommands.get(key);
    }
}