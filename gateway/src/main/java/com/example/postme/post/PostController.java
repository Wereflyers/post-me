package com.example.postme.post;

import com.example.postme.post.dto.NewPostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Validated
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostClient postClient;

    @GetMapping
    public ResponseEntity<Object> getAll(Principal principal, @RequestParam(defaultValue = "0") int from,
                                         @RequestParam(defaultValue = "10") int size) {
        log.info("Get all posts for user {}", principal.getName());
        return postClient.getAllForUser(principal.getName(), from, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(Principal principal, @PathVariable long id) {
        log.info("Get post {}", id);
        return postClient.get(principal.getName(), id);
    }

    @PostMapping
    public ResponseEntity<Object> add(Principal principal, @RequestBody NewPostDto newPostDto) {
        log.info("Creating post {}", newPostDto);
        return postClient.add(principal.getName(), newPostDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> update(Principal principal, @PathVariable long id,
                                  @RequestBody NewPostDto newPostDto) {
        log.info("Update post {} set {}", id, newPostDto);
        return postClient.update(principal.getName(), id, newPostDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(Principal principal, @PathVariable long id) {
        log.info("Delete post {}", id);
        return postClient.delete(principal.getName(), id);
    }

    @GetMapping("/sub")
    public ResponseEntity<Object> getAllFollowed(Principal principal, @RequestParam(defaultValue = "0") int from) {
        log.info("Get all posts by followed users for user {}", principal.getName());
        return postClient.getAllFollowed(principal.getName(), from);
    }
}
