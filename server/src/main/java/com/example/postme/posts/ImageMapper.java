package com.example.postme.posts;

import com.example.postme.posts.dto.ImageDto;
import com.example.postme.posts.dto.NewImageDto;

public class ImageMapper {
    public static Image toImage(NewImageDto newImageDto, long postId) {
        return Image.builder()
                .name(newImageDto.getName())
                .fileName(newImageDto.getFileName())
                .contentType(newImageDto.getContentType())
                .bytes(newImageDto.getBytes())
                .size(newImageDto.getSize())
                .fromPost(postId)
                .build();
    }

    public static ImageDto toImageDto(Image image) {
        return ImageDto.builder()
                .id(image.getId())
                .contentType(image.getContentType())
                .fileName(image.getFileName())
                .size(image.getSize())
                .bytes(image.getBytes())
                .name(image.getName())
                .build();
    }
}
