package com.example.postme.post;

import com.example.postme.post.dto.NewPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/{userId}/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostClient postClient;

    @GetMapping
    public ResponseEntity<Object> getAll(@PathVariable long userId, @RequestParam int from, @RequestParam int size) {
        log.info("Get all posts for user {}", userId);
        return postClient.getAllForUser(userId, from, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable long userId, @PathVariable long id) {
        log.info("Get post {}", id);
        return postClient.get(id,userId);
    }

    @PostMapping
    public ResponseEntity<Object> add(@PathVariable long userId, @RequestBody NewPostDto newPostDto) {
        log.info("Creating post {}", newPostDto);
        return postClient.add(userId, newPostDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable long userId, @PathVariable long id,
                                  @RequestBody NewPostDto newPostDto) {
        log.info("Update post {} set {}", id, newPostDto);
        return postClient.update(userId, id, newPostDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable long userId, @PathVariable long id) {
        log.info("Delete post {}", id);
        return postClient.delete(userId, id);
    }

    @GetMapping("/sub")
    public ResponseEntity<Object> getAllFollowed(@PathVariable long userId, @RequestParam int from) {
        log.info("Get all posts by followed users for user {}", userId);
        return postClient.getAllFollowed(userId, from);
    }
}
