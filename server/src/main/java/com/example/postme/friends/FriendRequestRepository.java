package com.example.postme.friends;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>, QuerydslPredicateExecutor<FriendRequest> {
    Optional<FriendRequest> findByIdAndUserOrSub(long id, long userId, long followedId);
    List<FriendRequest> findAllBySub(long userId, PageRequest pageRequest);
    Optional<FriendRequest> findByUserAndSub(long userId, long subId);
}
