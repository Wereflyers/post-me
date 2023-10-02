package com.example.postme.posts;

import com.example.postme.posts.dto.ImageDto;
import com.example.postme.posts.dto.NewImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService{
    private final ImageRepository imageRepository;

    @Transactional
    public void saveImage(NewImageDto newImageDto, long postId) {
        ImageMapper.toImageDto(imageRepository.save(ImageMapper.toImage(newImageDto, postId)));
    }

    @Override
    public List<ImageDto> getImage(long postId) {
        return imageRepository.findAllByFromPost(postId).stream()
                .map(ImageMapper::toImageDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteImages(long postId) {
        imageRepository.findAllByFromPost(postId).stream()
                .map(Image::getId)
                .forEach(imageRepository::deleteById);
    }
}
