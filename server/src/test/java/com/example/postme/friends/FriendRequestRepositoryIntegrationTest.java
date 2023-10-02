package com.example.postme.friends;

import com.example.postme.user.User;
import com.example.postme.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class FriendRequestRepositoryIntegrationTest {
    @Autowired
    private FriendRequestRepository friendRequestRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.save(User.builder()
                .email("john@mail.ru")
                .name("john")
                .build());
        userRepository.save(User.builder()
                .name("jade")
                .email("jade@mail.ru")
                .build());
        userRepository.save(User.builder()
                .name("jake")
                .email("jake@mail.ru")
                .build());
        userRepository.save(User.builder()
                .name("jane")
                .email("jane@mail.ru")
                .build());
        friendRequestRepository.save(new FriendRequest("john", "jane", RequestStatus.WAITING));
        friendRequestRepository.save(new FriendRequest("jade", "jane", RequestStatus.CANCELED));
        friendRequestRepository.save(new FriendRequest("jake", "jade", RequestStatus.ACCEPTED));
    }

    @Test
    void findAllBySubTest() {
        List<FriendRequest> requests = friendRequestRepository.findAllBySub("jane");

        assertEquals(2, requests.size());
        assertEquals("john", requests.get(0).getUser());
        assertEquals("jade", requests.get(1).getUser());
    }

    @Test
    void findByUserAndSubTest() {
        Optional<FriendRequest> request = friendRequestRepository.findByUserAndSub("jake", "jade");

        assertTrue(request.isPresent());
        assertEquals(3L, request.get().getId());
        assertEquals("jake", request.get().getUser());
        assertEquals("jade", request.get().getSub());
    }

    @Test
    void findAllByUserAndStatusTest() {
        List<FriendRequest> requestList = friendRequestRepository.findAllByUserAndStatus("jade", RequestStatus.CANCELED);

        assertEquals(1, requestList.size());
        assertEquals("jade", requestList.get(0).getUser());
        assertEquals("jane", requestList.get(0).getSub());
        assertEquals(RequestStatus.CANCELED, requestList.get(0).getStatus());
    }

    @Test
    void findByUserAndSubAndStatusTest() {
        Optional<FriendRequest> request = friendRequestRepository.findByUserAndSub("jake", "jade");

        assertTrue(request.isPresent());

        Optional<FriendRequest> request2 = friendRequestRepository.findByUserAndSubAndStatus("jake", "jade", RequestStatus.CANCELED);

        assertFalse(request2.isPresent());
    }

    @AfterEach
    public void clean() {
        friendRequestRepository.deleteAll();
        userRepository.deleteAll();
    }
}