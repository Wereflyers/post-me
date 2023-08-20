package com.example.postme.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends CrudRepository<UserAuth, Long> {
    Optional<UserAuth> findByUsername(String username);
}
