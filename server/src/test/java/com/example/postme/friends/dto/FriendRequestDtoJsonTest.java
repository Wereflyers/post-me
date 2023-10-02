package com.example.postme.friends.dto;

import com.example.postme.friends.RequestStatus;
import com.example.postme.user.dto.UserShort;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class FriendRequestDtoJsonTest {
    @Autowired
    private JacksonTester<FriendRequestDto> json;

    @SneakyThrows
    @Test
    void testPostDto() {
        FriendRequestDto friendRequestDto = FriendRequestDto.builder()
                .id(1L)
                .sub(new UserShort("sara"))
                .user(new UserShort("tina"))
                .status(RequestStatus.WAITING)
                .build();

        JsonContent<FriendRequestDto> result = json.write(friendRequestDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.user.name").isEqualTo("tina");
        assertThat(result).extractingJsonPathStringValue("$.sub.name").isEqualTo("sara");
        assertThat(result).extractingJsonPathStringValue("$.status").isEqualTo("WAITING");
    }
}