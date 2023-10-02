package com.example.postme.friends;

import com.example.postme.friends.dto.FriendRequestDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendRequestControllerTest {
    @InjectMocks
    private FriendRequestController friendRequestController;
    @Mock
    private FriendRequestServiceImpl friendRequestService;
    private final long requestId = 1L;

    @SneakyThrows
    @Test
    void addRequestTest() {
        when(friendRequestService.add("sara", "nina")).thenReturn(new FriendRequestDto());

        friendRequestController.addRequest("sara", "nina");

        verify(friendRequestService).add(anyString(), anyString());
    }

    @SneakyThrows
    @Test
    void getRequestsTest() {
        when(friendRequestService.getRequests("sara", 0)).thenReturn(Collections.singletonList(new FriendRequestDto()));

        friendRequestController.getRequests("sara", 0);

        verify(friendRequestService).getRequests(anyString(), anyInt());
    }

    @SneakyThrows
    @Test
    void getTest() {
        when(friendRequestService.get("sara", requestId)).thenReturn(new FriendRequestDto());

        friendRequestController.get("sara", requestId);

        verify(friendRequestService).get(anyString(), anyLong());
    }

    @SneakyThrows
    @Test
    void applyTest() {
        when(friendRequestService.apply("sara", requestId)).thenReturn(new FriendRequestDto());

        friendRequestController.apply("sara", requestId);

        verify(friendRequestService).apply(anyString(), anyLong());
    }

    @SneakyThrows
    @Test
    void cancelTest() {
        when(friendRequestService.cancel("sara", requestId)).thenReturn(new FriendRequestDto());

        friendRequestController.cancel("sara", requestId);

        verify(friendRequestService).cancel(anyString(), anyLong());
    }

    @SneakyThrows
    @Test
    void unsubTest() {
        friendRequestController.unsub("sara", "nina");

        verify(friendRequestService).unsub(anyString(), anyString());
    }
}