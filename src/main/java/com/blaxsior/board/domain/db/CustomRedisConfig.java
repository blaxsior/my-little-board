package com.blaxsior.board.domain.db;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CustomRedisConfig {
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        // @Transactional 지원
//        template.setEnableTransactionSupport(true);
        // jpa에 대해 정확하게 알지 못해 이유는 모르겠으나 Transactional을 켜두면 getValue 값이 null이 되는 문제가 있었음.
        // 이유를 나중에 알게 되고, 해결책을 찾으면 활성화 예정.
        return template;
    }
}
