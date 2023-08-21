package com.example.postme.post;

import com.example.postme.posts.PostController;
import com.example.postme.posts.PostMapper;
import com.example.postme.posts.PostService;
import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
class PostControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostService postService;
    private final long postId = 1L;
    private final String user = "username";
    private PostDto postDto;
    private NewPostDto newPostDto;

    @BeforeEach
    public void setUp() {
        postDto = PostDto.builder()
                .title("post")
                .id(postId)
                .description("new")
                .build();
        newPostDto = NewPostDto.builder()
                .title("itemName")
                .description("itemDescription")
                .build();
    }

    @SneakyThrows
    @Test
    void getAll() {
        when(postService.getAllForUser(user, 0, 100)).thenReturn(List.of(postDto));

        mockMvc.perform(get("/posts")
                        .header("username", user)
                        .param("from", "0")
                        .param("size", "100"))
                        .andExpect(status().isOk());

        verify(postService).getAllForUser(anyString(), anyInt(), anyInt());
    }

    @SneakyThrows
    @Test
    void get_Post() {
        when(postService.get(postId, user)).thenReturn(postDto);

        String result = mockMvc.perform(get("/posts/{postId}", postId)
                        .header("username", user))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, objectMapper.writeValueAsString(postDto));
        verify(postService).get(anyLong(), anyString());
    }

    @SneakyThrows
    @Test
    void add() {
        postDto = PostMapper.toPostDto(PostMapper.fromNewPostDto(newPostDto, user),null);

        when(postService.add(anyString(), any())).thenReturn(postDto);

        String result = mockMvc.perform(post("/posts")
                        .header("username", user)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newPostDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, objectMapper.writeValueAsString(postDto));
        verify(postService).add(anyString(), any());
    }

    @SneakyThrows
    @Test
    void update() {
        postDto = PostMapper.toPostDto(PostMapper.fromNewPostDto(newPostDto, user),null);

        when(postService.update(anyString(), anyLong(), any())).thenReturn(postDto);

        String result = mockMvc.perform(patch("/posts/{postId}", postId)
                        .header("username", user)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(newPostDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals(result, objectMapper.writeValueAsString(postDto));
        verify(postService).update(anyString(), anyLong(), any());
    }

    @SneakyThrows
    @Test
    void deleteItem() {
        mockMvc.perform(delete("/posts/{postId}", postId)
                        .header("username", user))
                .andExpect(status().isNoContent());

        verify(postService).delete(anyString(), anyLong());
    }

    @SneakyThrows
    @Test
    void getAllFollowed() {
        when(postService.getAllFollowed(user, 0)).thenReturn(List.of(postDto));

        mockMvc.perform(get("/posts/sub")
                        .header("username", user)
                        .param("from", "0"))
                .andExpect(status().isOk());

        verify(postService).getAllFollowed(user, 0);
    }
}