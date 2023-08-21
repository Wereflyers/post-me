package com.example.postme.post.dto;

import com.example.postme.posts.dto.NewImageDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class NewImageDtoJsonTest {
    @Autowired
    private JacksonTester<NewImageDto> json;

    @SneakyThrows
    @Test
    void testNewImageDto() {
        NewImageDto imageDto = new NewImageDto();
        imageDto.setName("text");
        imageDto.setContentType("jpg");
        imageDto.setSize(2L);
        imageDto.setFileName("name");

        JsonContent<NewImageDto> result = json.write(imageDto);

        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("text");
        assertThat(result).extractingJsonPathNumberValue("$.size").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.fileName").isEqualTo("name");
        assertThat(result).extractingJsonPathStringValue("$.contentType").isEqualTo("jpg");
    }
}