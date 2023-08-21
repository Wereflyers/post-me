package com.example.postme.posts;

import com.example.postme.posts.dto.ImageDto;
import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import com.example.postme.user.dto.UserShort;

import java.time.LocalDateTime;
import java.util.List;

public class PostMapper {
    public static Post fromNewPostDto(NewPostDto newPostDto, String username) {
        return Post.builder()
                .title(newPostDto.getTitle())
                .description(newPostDto.getDescription())
                .creator(username)
                .publishedOn(LocalDateTime.now())
                .build();
    }

    public static PostDto toPostDto(Post post, List<ImageDto> images) {
        return PostDto.builder()
                .id(post.getId())
                .creator(new UserShort(post.getCreator()))
                .title(post.getTitle())
                .description(post.getDescription())
                .images(images)
                .publishedOn(post.getPublishedOn())
                .build();
    }
}
