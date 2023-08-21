package com.example.postme.post.dto;

import com.example.postme.posts.dto.ImageDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ImageDtoJsonTest {
    @Autowired
    private JacksonTester<ImageDto> json;

    @SneakyThrows
    @Test
    void testImageDto() {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(1L);
        imageDto.setName("text");
        imageDto.setContentType("jpg");
        imageDto.setSize(2L);
        imageDto.setFileName("name");

        JsonContent<ImageDto> result = json.write(imageDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("text");
        assertThat(result).extractingJsonPathNumberValue("$.size").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.fileName").isEqualTo("name");
        assertThat(result).extractingJsonPathStringValue("$.contentType").isEqualTo("jpg");
    }
}