package com.example.postme.post.dto;

import com.example.postme.posts.dto.PostDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class PostDtoJsonTest {
    @Autowired
    private JacksonTester<PostDto> json;

    @SneakyThrows
    @Test
    void testPostDto() {
        PostDto postDto = PostDto.builder()
                .id(1L)
                .title("name")
                .description("description")
                .build();

        JsonContent<PostDto> result = json.write(postDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.title").isEqualTo("name");
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("description");
    }
}