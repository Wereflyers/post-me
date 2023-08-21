package com.example.postme.post.dto;

import com.example.postme.posts.dto.NewImageDto;
import com.example.postme.posts.dto.NewPostDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class NewPostDtoJsonTest {
    @Autowired
    private JacksonTester<NewPostDto> json;

    @SneakyThrows
    @Test
    void testNewPostDto() {
        NewImageDto imageDto = new NewImageDto();
        imageDto.setName("text");
        imageDto.setContentType("jpg");
        imageDto.setSize(2L);
        imageDto.setFileName("name");

        NewPostDto newPostDto = NewPostDto.builder()
                .title("name")
                .description("description")
                .images(List.of(imageDto))
                .build();

        JsonContent<NewPostDto> result = json.write(newPostDto);

        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo("name");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
        assertThat(result).extractingJsonPathStringValue("$.images[0].name").isEqualTo("text");
        assertThat(result).extractingJsonPathStringValue("$.images[0].contentType").isEqualTo("jpg");
        assertThat(result).extractingJsonPathNumberValue("$.images[0].size").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.images[0].fileName").isEqualTo("name");
    }
}