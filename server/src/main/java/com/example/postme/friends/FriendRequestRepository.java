package com.example.postme.friends;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>, QuerydslPredicateExecutor<FriendRequest> {

    List<FriendRequest> findAllBySub(String username);

    Optional<FriendRequest> findByUserAndSub(String user, String sub);

    List<FriendRequest> findAllByUserAndStatus(String userName, RequestStatus status);

    Optional<FriendRequest> findByUserAndSubAndStatus(String user, String sub, RequestStatus status);
}
