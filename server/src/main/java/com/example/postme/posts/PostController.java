package com.example.postme.posts;

import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDto> getAll(@RequestHeader long userId, @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        return postService.getAllForUser(userId, from, size);
    }

    @GetMapping("/{id}")
    public PostDto get(@RequestHeader long userId, @PathVariable long id) {
        return postService.get(id,userId);
    }

    @PostMapping
    public PostDto add(@RequestHeader long userId, @RequestBody NewPostDto newPostDto) {
        return postService.add(userId, newPostDto);
    }

    @PatchMapping("/{id}")
    public PostDto update(@RequestHeader long userId, @PathVariable long id,
                                  @RequestBody NewPostDto newPostDto) {
        return postService.update(userId, id, newPostDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader long userId, @PathVariable long id) {
        postService.delete(userId, id);
    }

    @GetMapping("/sub")
    public List<PostDto> getAllFollowed(@RequestHeader long userId, @RequestParam(defaultValue = "0") int from) {
        return postService.getAllFollowed(userId, from);
    }
}
