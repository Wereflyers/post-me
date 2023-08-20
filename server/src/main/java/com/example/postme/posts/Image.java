package com.example.postme.posts;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] bytes;
    @Column(name = "from_post")
    private Long fromPost;
}
