package com.example.postme.post;

import com.example.postme.exception.ValidationException;
import com.example.postme.posts.PostController;
import com.example.postme.posts.PostService;
import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @InjectMocks
    private PostController postController;
    @Mock
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
    void getAllTest() {
        when(postService.getAllForUser(user, 0, 10)).thenReturn(List.of(postDto));

        List<PostDto> response = postController.getAll(user, 0, 10);

        verify(postService).getAllForUser(user, 0, 10);
        assertEquals(response.size(), 1);
        assertEquals(response.get(0), postDto);
    }

    @Test
    void getAllFromExceptionTest() {
        assertThrows(ValidationException.class, () -> postController.getAll(user, -1, 10));
        verify(postService, never()).getAllForUser(anyString(), anyInt(), anyInt());
    }

    @Test
    void getAllSizeExceptionTest() {
        assertThrows(ValidationException.class, () -> postController.getAll(user, 0, 0));
        verify(postService, never()).getAllForUser(anyString(), anyInt(), anyInt());
    }

    @SneakyThrows
    @Test
    void getPostTest() {
        when(postService.get(postId, user)).thenReturn(postDto);

        PostDto response = postController.get(user, postId);

        verify(postService).get(postId, user);
        assertEquals(postDto, response);
    }

    @SneakyThrows
    @Test
    void addTest() {
        when(postService.add(user, newPostDto)).thenReturn(postDto);

        PostDto response = postController.add(user, newPostDto);

        verify(postService).add(user, newPostDto);
        assertEquals(postDto, response);
    }

    @SneakyThrows
    @Test
    void updateTest() {
        when(postService.update(user, postId, newPostDto)).thenReturn(postDto);

        PostDto response = postController.update(user, postId, newPostDto);

        verify(postService).update(user, postId, newPostDto);
        assertEquals(postDto, response);
    }

    @SneakyThrows
    @Test
    void deleteItemTest() {
        postController.delete(user, postId);

        verify(postService).delete(user, postId);
    }

    @SneakyThrows
    @Test
    void getAllFollowedTest() {
        when(postService.getAllFollowed(user, 0)).thenReturn(List.of(postDto));

        List<PostDto> response = postController.getAllFollowed(user, 0);

        verify(postService).getAllFollowed(user, 0);
        assertEquals(response.size(), 1);
        assertEquals(response.get(0), postDto);
    }

    @Test
    void getAllFollowedExceptionTest() {
        assertThrows(ValidationException.class, () -> postController.getAllFollowed(user, -1));
        verify(postService, never()).getAllFollowed(anyString(), anyInt());
    }
}