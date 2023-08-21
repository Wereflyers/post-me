package com.example.postme.post.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewImageDto {
    private String name;
    private String fileName;
    private Long size;
    private String contentType;
    private byte[] bytes;
}
