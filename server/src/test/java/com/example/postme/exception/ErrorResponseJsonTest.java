package com.example.postme.exception;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ErrorResponseJsonTest {
    @Autowired
    private JacksonTester<ApiError> json;

    @SneakyThrows
    @Test
    void testErrorResponse() {
        ApiError errorResponse = new ApiError("CONFLICT","message", "reason");

        JsonContent<ApiError> result = json.write(errorResponse);

        assertThat(result).hasJsonPathStringValue("$.status", "CONFLICT");
        assertThat(result).hasJsonPathStringValue("$.message", "message");
        assertThat(result).hasJsonPathStringValue("$.reason", "reason");
    }
}