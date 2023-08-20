package com.example.postme.posts;

import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import com.example.postme.user.dto.UserShort;

import java.time.LocalDateTime;

public class PostMapper {
    public static Post fromNewPostDto(NewPostDto newPostDto, String username) {
        return Post.builder()
                .title(newPostDto.getTitle())
                .description(newPostDto.getDescription())
                .image(newPostDto.getImage())
                .creator(username)
                .publishedOn(LocalDateTime.now())
                .build();
    }

    public static PostDto toPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .creator(new UserShort(post.getCreator()))
                .title(post.getTitle())
                .description(post.getDescription())
                .image(post.getImage())
                .publishedOn(post.getPublishedOn())
                .build();
    }
}
