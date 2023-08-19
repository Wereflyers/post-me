package com.example.postme.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestClient friendRequestClient;

    @PostMapping("/{followedId}")
    public ResponseEntity<Object> addRequest(@RequestHeader long userId, @PathVariable long followedId) {
        return friendRequestClient.add(userId, followedId);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(@RequestHeader long userId,
                                                    @RequestParam(value = "from", defaultValue = "0") int from) {
        return friendRequestClient.getRequests(userId, from);
    }

    //TODO disperancy between swagger
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> get(@RequestHeader long userId, @PathVariable long requestId) {
        return friendRequestClient.get(userId, requestId);
    }

    @PatchMapping("/{requestId}/apply")
    public ResponseEntity<Object> apply(@RequestHeader long userId, @PathVariable long requestId) {
        return friendRequestClient.apply(userId, requestId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancel(@RequestHeader long userId, @PathVariable long requestId) {
        return friendRequestClient.cancel(userId, requestId);
    }

    @DeleteMapping("/{followedId}")
    public ResponseEntity<Object> unsub(@RequestHeader long userId, @PathVariable long followedId) {
        return friendRequestClient.unsub(userId, followedId);
    }
}
