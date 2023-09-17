package com.example.postme.redis;

import com.example.postme.user.UserAuth;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataRedisTest

public class RedisRepositoryTest {

    @Autowired
    private RedisRepository redisRepository;
    private UserAuth user;

    @Before
    public void setUp() {
        user = new UserAuth();
        user.setUsername("username");
        user.setPassword("password");
    }

    @Test
    public void createUserTest() {
        assertThat(redisRepository.save(user)).isEqualTo(user);
    }

    @Test
    public void whenFindByName_thenReturnUser() {
        redisRepository.save(user);
        UserAuth found = redisRepository.findByUsername(user.getUsername()).get();
        assertThat(found).isEqualTo(user);
    }

}
