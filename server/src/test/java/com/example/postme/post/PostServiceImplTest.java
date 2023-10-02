package com.example.postme.post;

import com.example.postme.exception.WrongConditionException;
import com.example.postme.friends.FriendRequest;
import com.example.postme.friends.FriendRequestRepository;
import com.example.postme.friends.RequestStatus;
import com.example.postme.posts.*;
import com.example.postme.posts.dto.ImageDto;
import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @InjectMocks
    private PostServiceImpl postService;
    @Mock
    private PostRepository postRepository;
    @Mock
    private FriendRequestRepository friendRequestRepository;
    @Mock
    private ImageServiceImpl imageService;
    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    private final long postId = 1L;
    private final String username = "username";
    private Post post;
    private NewPostDto newPostDto;
    private ImageDto image;
    private FriendRequest friendRequest;

    @BeforeEach
    public void setUp() {
        post = Post.builder()
                .id(postId)
                .title("post")
                .creator(username)
                .description("new")
                .publishedOn(LocalDateTime.of(2023,12,1,12,20))
                .build();
        newPostDto = NewPostDto.builder()
                .title("post")
                .description("new")
                .build();
        image = ImageDto.builder()
                .name("image")
                .fileName("filename")
                .bytes(new byte[]{})
                .size(20L)
                .build();
        friendRequest = FriendRequest.builder()
                .user("followedUser")
                .sub(username)
                .status(RequestStatus.ACCEPTED)
                .build();
    }

    @Test
    void addTest() {
        when(postRepository.save(any())).thenReturn(post);

        PostDto result = postService.add(username, newPostDto);

        assertEquals(result, PostMapper.toPostDto(post, new ArrayList<>()));
    }

    @Test
    void getAllForUserTest() {
        when(postRepository.findAllByCreatorOrderByPublishedOn(anyString(), any())).thenReturn(List.of(post));

        List<PostDto> result = postService.getAllForUser(username, 0, 10);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), PostMapper.toPostDto(post, new ArrayList<>()));
    }

    @Test
    void getTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(imageService.getImage(postId)).thenReturn(List.of(image));

        PostDto result = postService.get(postId, username);

        assertEquals(result, PostMapper.toPostDto(post, List.of(image)));
    }

    @Test
    void getExceptionTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> postService.get(postId, username));
    }

    @Test
    void updateTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(imageService.getImage(postId)).thenReturn(new ArrayList<>());
        when(postRepository.save(any())).thenReturn(post);

        postService.update(username, postId, newPostDto);

        verify(postRepository).save(postArgumentCaptor.capture());
        Post savedPost = postArgumentCaptor.getValue();

        assertEquals(savedPost.getId(), postId);
        assertEquals(savedPost.getTitle(), newPostDto.getTitle());
        assertEquals(savedPost.getDescription(), newPostDto.getDescription());
        assertEquals(savedPost.getCreator(), username);
    }

    @Test
    void updateNullPointerExceptionTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> postService.update(username, postId, newPostDto));
    }

    @Test
    void updateWrongConditionExceptionTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        assertThrows(WrongConditionException.class, () -> postService.update("f", postId, newPostDto));
    }

    @Test
    void deleteTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        postService.delete(username, postId);

        verify(postRepository).deleteById(postId);
    }

    @Test
    void deleteNullPointerExceptionTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> postService.delete(username, postId));
    }

    @Test
    void deleteWrongConditionExceptionTest() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        assertThrows(WrongConditionException.class, () -> postService.delete("2L", postId));
    }

    @Test
    void getAllFollowedTest() {
        when(friendRequestRepository.findAllBySub(anyString())).thenReturn(List.of(friendRequest));
        when(friendRequestRepository.findAllByUserAndStatus(username, RequestStatus.ACCEPTED)).thenReturn(new ArrayList<>());
        when(postRepository.findAllByCreatorInOrderByPublishedOnDesc(List.of("followedUser"), PageRequest.of(0, 10))).thenReturn(List.of(post));
        when(imageService.getImage(postId)).thenReturn(new ArrayList<>());

        List<PostDto> result = postService.getAllFollowed(username, 0);

        assertEquals(result.size(), 1);
        assertEquals(result.get(0), PostMapper.toPostDto(post, new ArrayList<>()));
    }
}