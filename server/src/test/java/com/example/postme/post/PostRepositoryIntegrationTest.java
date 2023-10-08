package com.example.postme.post;

import com.example.postme.posts.Post;
import com.example.postme.posts.PostRepository;
import com.example.postme.user.User;
import com.example.postme.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PostRepositoryIntegrationTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    private Post post;
    private Post post2;
    private final String username = "username";
    private final String username2 = "username2";

    @BeforeEach
    public void setUp() {
        userRepository.save(User.builder()
                .email("john@mail.ru")
                .name(username)
                .build());
        userRepository.save(User.builder()
                .name(username2)
                .email("jane@mail.ru")
                .build());
        post = postRepository.save(Post.builder()
                .title("post")
                .creator(username)
                .description("new")
                .publishedOn(LocalDateTime.of(2023,12,1,12,20))
                .build());
        post2 = postRepository.save(Post.builder()
                .title("postTwo")
                .creator(username2)
                .description("newTwo")
                .publishedOn(LocalDateTime.of(2022,12,1,12,20))
                .build());
    }

    @Test
    void findAllByCreatorOrderByPublishedOnTest() {
        List<Post> posts = postRepository.findAllByCreatorOrderByPublishedOn(username, PageRequest.of(0, 10));

        assertEquals(posts.size(), 1);
        assertEquals(posts.get(0), post);
    }

    @Test
    void findAllByCreatorInOrderByPublishedOnDescTest() {
        List<Post> posts = postRepository.findAllByCreatorInOrderByPublishedOnDesc(List.of(username, username2), PageRequest.of(0, 10));

        assertEquals(posts.size(), 2);
        assertEquals(posts.get(0), post);
        assertEquals(posts.get(1), post2);
    }

    @AfterEach
    public void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
}