package com.example.postme.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User> {

    /**
     * Поиск всех пользователей по емейлу.
     * @param email - емейл
     * @return List
     */
    List<User> findAllByEmail(String email);

}