package com.api.redis.repository;

import com.api.redis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String KEY = "USER";


    public User findByEmail(String email) {
        Map<Object, Object> users = redisTemplate.opsForHash().entries(KEY);

        return users.values().stream()
                .map(u -> (User) u)
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }


    public User save(User user){
        redisTemplate.opsForHash().put(KEY, user.getId(), user);
        return user;
    }

    public User get(String id){
        return (User) redisTemplate.opsForHash().get(KEY,id);
    }


    public Map<Object, Object> findAll() {
        return redisTemplate.opsForHash().entries(KEY);
    }

    public void deleteById(String id) {
        redisTemplate.opsForHash().delete(KEY, id);
    }
}
