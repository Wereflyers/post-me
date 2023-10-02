package com.example.postme.post;

import com.example.postme.posts.Image;
import com.example.postme.posts.ImageRepository;
import com.example.postme.posts.ImageServiceImpl;
import com.example.postme.posts.dto.ImageDto;
import com.example.postme.posts.dto.NewImageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @InjectMocks
    private ImageServiceImpl imageService;

    @Mock
    private ImageRepository imageRepository;

    private Image image;
    private NewImageDto newImageDto;

    private final long postId = 1L;

    @BeforeEach
    public void create() {
        image = Image.builder()
                .name("image")
                .fileName("filename")
                .bytes(new byte[]{})
                .contentType("jpg")
                .size(20L)
                .fromPost(postId)
                .build();
        newImageDto = NewImageDto.builder()
                .name("image")
                .fileName("filename")
                .bytes(new byte[]{})
                .contentType("jpg")
                .size(20L)
                .build();
    }

    @Test
    void saveTest() {
        when(imageRepository.save(any())).thenReturn(image);

        imageService.saveImage(newImageDto, postId);

        verify(imageRepository).save(any());
    }

    @Test
    void getImagesTest() {
        when(imageRepository.findAllByFromPost(postId)).thenReturn(List.of(image));

        List<ImageDto> imageDtoList = imageService.getImage(postId);

        assertEquals(imageDtoList.size(), 1);
        assertEquals(imageDtoList.get(0).getName(), "image");
        assertEquals(imageDtoList.get(0).getSize(), 20L);
        assertEquals(imageDtoList.get(0).getContentType(), "jpg");
        assertEquals(imageDtoList.get(0).getFileName(), "filename");
    }

    @Test
    void deleteImagesTest() {
        when(imageRepository.findAllByFromPost(postId)).thenReturn(List.of(image));

        verify(imageRepository).deleteById(postId);
    }
}