package com.example.postme.posts;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    Long id;
    String title;
    String description;
    String creator;
    @Column(name = "published")
    @DateTimeFormat
    LocalDateTime publishedOn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (!Objects.equals(id, post.id)) return false;
        if (!title.equals(post.title)) return false;
        if (!description.equals(post.description)) return false;
        if (!creator.equals(post.creator)) return false;
        return publishedOn.equals(post.publishedOn);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + creator.hashCode();
        result = 31 * result + publishedOn.hashCode();
        return result;
    }
}
