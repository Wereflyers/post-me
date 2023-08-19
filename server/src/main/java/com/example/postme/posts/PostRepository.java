package com.example.postme.posts;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
    List<Post> findAllByCreatorOrderByPublishedOn(long userId, PageRequest pageRequest);
    List<Post> findAllByCreatorInOrderByPublishedOnDesc(List<Long> userId, PageRequest pageRequest);
}
