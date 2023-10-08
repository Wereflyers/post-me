package com.example.postme.post;

import com.example.postme.posts.Image;
import com.example.postme.posts.ImageRepository;
import com.example.postme.posts.Post;
import com.example.postme.posts.PostRepository;
import com.example.postme.user.User;
import com.example.postme.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class ImageRepositoryIntegrationTest {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageRepository imageRepository;
    private Image image;
    private Post post;

    @BeforeEach
    public void setUp() {
        String username = "username";
        userRepository.save(User.builder()
                .email("john@mail.ru")
                .name(username)
                .build());
        post = postRepository.save(Post.builder()
                .title("post")
                .creator(username)
                .description("new")
                .publishedOn(LocalDateTime.of(2023,12,1,12,20))
                .build());
        image = imageRepository.save(Image.builder()
                .fromPost(post.getId())
                .name("image")
                .fileName("filename")
                .bytes(new byte[]{1, 2})
                .contentType("jpg")
                .size(20L)
                .build());
    }

    @Test
    void findAllByFromPostTest() {
        List<Image> images = imageRepository.findAllByFromPost(post.getId());

        assertEquals(1, images.size());
        assertEquals(image, images.get(0));
    }

    @AfterEach
    public void clean() {
        imageRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }
}