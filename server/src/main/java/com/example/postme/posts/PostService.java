package com.example.postme.posts;

import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;

import java.util.List;

public interface PostService {
    //TODO docs

    List<PostDto> getAllForUser(String username, int from, int size);

    PostDto add(String username, NewPostDto newPostDto);

    PostDto get(long id, String username);

    PostDto update(String username, long id, NewPostDto newPostDto);

    void delete(String username, long id);

    List<PostDto> getAllFollowed(String username, int from);
}
