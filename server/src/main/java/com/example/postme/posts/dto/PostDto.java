package com.example.postme.posts.dto;

import com.example.postme.user.dto.UserShort;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {
    Long id;
    UserShort creator;
    LocalDateTime publishedOn;
    String title;
    String description;
    //TODO images
    String image;
}
