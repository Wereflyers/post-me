package com.example.postme.posts;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "images")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "size")
    private Long size;
    @Column(name = "content_type")
    private String contentType;
    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] bytes;
    @Column(name = "from_post")
    private Long fromPost;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (!Objects.equals(id, image.id)) return false;
        if (!name.equals(image.name)) return false;
        if (!fileName.equals(image.fileName)) return false;
        if (!Objects.equals(size, image.size)) return false;
        if (!contentType.equals(image.contentType)) return false;
        if (!Arrays.equals(bytes, image.bytes)) return false;
        return Objects.equals(fromPost, image.fromPost);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + fileName.hashCode();
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + contentType.hashCode();
        result = 31 * result + Arrays.hashCode(bytes);
        result = 31 * result + (fromPost != null ? fromPost.hashCode() : 0);
        return result;
    }
}
