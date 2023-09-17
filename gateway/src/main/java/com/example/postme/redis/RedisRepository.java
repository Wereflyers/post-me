package com.example.postme.redis;

import com.example.postme.user.UserAuth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisRepository extends CrudRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);
}