package com.example.postme.posts;

import com.example.postme.exception.ValidationException;
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
    public List<PostDto> getAll(@RequestHeader String username, @RequestParam(defaultValue = "0") int from,
                                @RequestParam(defaultValue = "10") int size) {
        if (from < 0 || size < 1) {
            throw new ValidationException("Wrong parameters");
        }
        return postService.getAllForUser(username, from, size);
    }

    @GetMapping("/{id}")
    public PostDto get(@RequestHeader String username, @PathVariable long id) {
        return postService.get(id,username);
    }

    @PostMapping
    public PostDto add(@RequestHeader String username, @RequestBody NewPostDto newPostDto) {
        return postService.add(username, newPostDto);
    }

    @PatchMapping("/{id}")
    public PostDto update(@RequestHeader String username, @PathVariable long id,
                                  @RequestBody NewPostDto newPostDto) {
        return postService.update(username, id, newPostDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestHeader String username, @PathVariable long id) {
        postService.delete(username, id);
    }

    @GetMapping("/sub")
    public List<PostDto> getAllFollowed(@RequestHeader String username, @RequestParam(defaultValue = "0") int from) {
        if (from < 0) {
            throw new ValidationException("Wrong parameters");
        }
        return postService.getAllFollowed(username, from);
    }
}
