package com.example.postme.posts.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewPostDto {
    String title;
    String description;
    List<NewImageDto> images;
}
