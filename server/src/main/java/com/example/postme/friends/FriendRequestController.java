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

    @PostMapping("/{followedId}")
    public FriendRequestDto addRequest(@RequestHeader long userId, @PathVariable long followedId) {
        return friendRequestService.add(userId, followedId);
    }

    @GetMapping
    public List<FriendRequestDto> getRequests(@RequestHeader long userId,
                                                    @RequestParam(value = "from", defaultValue = "0") int from) {
        return friendRequestService.getRequests(userId, from);
    }

    //TODO disperancy between swagger
    @GetMapping("/{requestId}")
    public FriendRequestDto get(@RequestHeader long userId, @PathVariable long requestId) {
        return friendRequestService.get(userId, requestId);
    }

    @PatchMapping("/{requestId}/apply")
    public FriendRequestDto apply(@RequestHeader long userId, @PathVariable long requestId) {
        return friendRequestService.apply(userId, requestId);
    }

    @PatchMapping("/{requestId}/cancel")
    public FriendRequestDto cancel(@RequestHeader long userId, @PathVariable long requestId) {
        return friendRequestService.cancel(userId, requestId);
    }

    @DeleteMapping("/{followedId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unsub(@RequestHeader long userId, @PathVariable long followedId) {
        friendRequestService.unsub(userId, followedId);
    }
}
