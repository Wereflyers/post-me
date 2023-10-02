/*package com.example.postme.redis;

import com.example.postme.user.dto.UserDto;
import lombok.SneakyThrows;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {
    @SneakyThrows
    @Test
    void add_whenEmptyName_thenReturnValidationException() {
        user1.setUsername("");
        when(userService.add(user1)).thenReturn(user1);

        String result = mockMvc.perform(post("/users")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(userToCreate)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        UserDto actualUser = userController.add(user1);

        assertEquals(user1.getUsername(), actualUser.getUsername());
        assertEquals(user1.getEmail(), actualUser.getEmail());
        verify(userService).add(user1);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> userService.get(userId));
        verify(userRepository, atMostOnce()).findById(userId);
    }
}*/
