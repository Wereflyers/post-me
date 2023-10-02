package com.example.postme.posts;

import com.example.postme.posts.dto.ImageDto;
import com.example.postme.posts.dto.NewImageDto;

import java.util.List;

public interface ImageService {

    void saveImage(NewImageDto newImageDto, long postId);

    List<ImageDto> getImage(long postId);

    void deleteImages(long postId);
}
