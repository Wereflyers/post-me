package com.example.postme.friends;

import com.example.postme.friends.dto.FriendRequestDto;
import com.example.postme.user.dto.UserShort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FriendRequestController.class)
@AutoConfigureMockMvc
class FriendRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FriendRequestService friendRequestService;
    private FriendRequestDto friendRequestDto;

    @BeforeEach
    void setUp() {
        friendRequestDto = FriendRequestDto.builder()
                .id(1L)
                .sub(new UserShort("sub"))
                .user(new UserShort("user"))
                .status(RequestStatus.WAITING)
                .build();
    }

    @SneakyThrows
    @Test
    void addRequestTest() {
        when(friendRequestService.add(anyString(), anyString())). thenReturn(friendRequestDto);

        String result = mockMvc.perform(post("/requests/{followedName}", "user")
                        .header("username", "name"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(friendRequestDto), result);
    }

    @SneakyThrows
    @Test
    void getRequestsTest() {
        when(friendRequestService.getRequests(anyString(), anyInt())). thenReturn(Collections.singletonList(friendRequestDto));

        String result = mockMvc.perform(get("/requests/")
                        .header("username", "name")
                        .param("from", "0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(Collections.singletonList(friendRequestDto)), result);
    }

    @SneakyThrows
    @Test
    void getTest() {
        when(friendRequestService.get(anyString(), anyLong())). thenReturn(friendRequestDto);

        String result = mockMvc.perform(get("/requests/{requestId}", 1L)
                        .header("username", "name"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(friendRequestDto), result);
    }

    @SneakyThrows
    @Test
    void applyTest() {
        when(friendRequestService.apply(anyString(), anyLong())). thenReturn(friendRequestDto);

        String result = mockMvc.perform(patch("/requests/{requestId}/apply", 1L)
                        .header("username", "name"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(friendRequestDto), result);
    }

    @SneakyThrows
    @Test
    void cancelTest() {
        when(friendRequestService.cancel(anyString(), anyLong())). thenReturn(friendRequestDto);

        String result = mockMvc.perform(patch("/requests/{requestId}/cancel", 1L)
                        .header("username", "name"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(objectMapper.writeValueAsString(friendRequestDto), result);
    }

    @SneakyThrows
    @Test
    void unsubTest() {
        mockMvc.perform(delete("/requests/{followedName}", "nameFollowedUser")
                        .header("username", "name"))
                .andExpect(status().isNoContent());

        verify(friendRequestService).unsub("name", "nameFollowedUser");
    }
}