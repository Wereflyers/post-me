package com.example.postme.friends;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class FriendRequestController {
    private final FriendRequestClient friendRequestClient;

    @PostMapping("/{followedName}")
    public ResponseEntity<Object> addRequest(Principal principal, @PathVariable String followedName) {
        log.info("Create friend request from {} to {}", principal.getName(), followedName);
        return friendRequestClient.add(principal.getName(), followedName);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(Principal principal,
                                                    @RequestParam(value = "from", defaultValue = "0") int from) {
        log.info("Get requests for {}", principal.getName());
        return friendRequestClient.getRequests(principal.getName(), from);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> get(Principal principal, @PathVariable long requestId) {
        log.info("Get request {} for {}", requestId, principal.getName());
        return friendRequestClient.get(principal.getName(), requestId);
    }

    @PatchMapping("/{requestId}/apply")
    public ResponseEntity<Object> apply(Principal principal, @PathVariable long requestId) {
        log.info("Apply request {}", requestId);
        return friendRequestClient.apply(principal.getName(), requestId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancel(Principal principal, @PathVariable long requestId) {
        log.info("Cancel request {}", requestId);
        return friendRequestClient.cancel(principal.getName(), requestId);
    }

    @DeleteMapping("/{followedName}")
    public ResponseEntity<Object> unsub(Principal principal, @PathVariable String followedName) {
        log.info("User {} unsub from user {}", principal.getName(), followedName);
        return friendRequestClient.unsub(principal.getName(), followedName);
    }
}
