package com.example.postme.posts;

import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;

import java.util.List;

public interface PostService {
    //TODO docs

    List<PostDto> getAllForUser(long userId, int from, int size);

    PostDto add(long userId, NewPostDto newPostDto);

    PostDto get(long id, long userId);

    PostDto update(long userId, long id, NewPostDto newPostDto);

    void delete(long userId, long id);

    List<PostDto> getAllFollowed(long userId, int from);
}
