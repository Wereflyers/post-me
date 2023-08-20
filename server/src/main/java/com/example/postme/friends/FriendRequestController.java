package com.example.postme.friends;

import com.example.postme.friends.dto.FriendRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @Autowired
    public FriendRequestController(FriendRequestService friendRequestService) {
        this.friendRequestService = friendRequestService;
    }

    @PostMapping("/{followedName}")
    public FriendRequestDto addRequest(@RequestHeader String username, @PathVariable String followedName) {
        return friendRequestService.add(username, followedName);
    }

    @GetMapping
    public List<FriendRequestDto> getRequests(@RequestHeader String username,
                                                    @RequestParam(value = "from", defaultValue = "0") int from) {
        return friendRequestService.getRequests(username, from);
    }

    //TODO disperancy between swagger
    @GetMapping("/{requestId}")
    public FriendRequestDto get(@RequestHeader String username, @PathVariable long requestId) {
        return friendRequestService.get(username, requestId);
    }

    @PatchMapping("/{requestId}/apply")
    public FriendRequestDto apply(@RequestHeader String username, @PathVariable long requestId) {
        return friendRequestService.apply(username, requestId);
    }

    @PatchMapping("/{requestId}/cancel")
    public FriendRequestDto cancel(@RequestHeader String username, @PathVariable long requestId) {
        return friendRequestService.cancel(username, requestId);
    }

    @DeleteMapping("/{followedName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsub(@RequestHeader String username, @PathVariable String followedName) {
        friendRequestService.unsub(username, followedName);
    }
}
