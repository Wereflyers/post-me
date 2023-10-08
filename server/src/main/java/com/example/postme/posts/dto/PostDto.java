package com.example.postme.posts.dto;

import com.example.postme.user.dto.UserShort;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
    List<ImageDto> images;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostDto postDto = (PostDto) o;

        if (!Objects.equals(id, postDto.id)) return false;
        if (!creator.getName().equals(postDto.creator.getName())) return false;
        if (!publishedOn.equals(postDto.publishedOn)) return false;
        if (!title.equals(postDto.title)) return false;
        if (!description.equals(postDto.description)) return false;
        return Objects.equals(images, postDto.images);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + creator.getName().hashCode();
        result = 31 * result + publishedOn.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (images != null ? images.hashCode() : 0);
        return result;
    }
}
