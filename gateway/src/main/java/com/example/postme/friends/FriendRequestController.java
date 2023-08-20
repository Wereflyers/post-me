package com.example.postme.friends;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestClient friendRequestClient;

    @PostMapping("/{followedName}")
    public ResponseEntity<Object> addRequest(Principal principal, @PathVariable String followedName) {
        return friendRequestClient.add(principal.getName(), followedName);
    }

    @GetMapping
    public ResponseEntity<Object> getRequests(Principal principal,
                                                    @RequestParam(value = "from", defaultValue = "0") int from) {
        return friendRequestClient.getRequests(principal.getName(), from);
    }

    //TODO disperancy between swagger
    @GetMapping("/{requestId}")
    public ResponseEntity<Object> get(Principal principal, @PathVariable long requestId) {
        return friendRequestClient.get(principal.getName(), requestId);
    }

    @PatchMapping("/{requestId}/apply")
    public ResponseEntity<Object> apply(Principal principal, @PathVariable long requestId) {
        return friendRequestClient.apply(principal.getName(), requestId);
    }

    @PatchMapping("/{requestId}/cancel")
    public ResponseEntity<Object> cancel(Principal principal, @PathVariable long requestId) {
        return friendRequestClient.cancel(principal.getName(), requestId);
    }

    @DeleteMapping("/{followedName}")
    public ResponseEntity<Object> unsub(Principal principal, @PathVariable String followedName) {
        return friendRequestClient.unsub(principal.getName(), followedName);
    }
}
