package com.example.postme.friends;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Entity
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "friend_requests")
@NoArgsConstructor
public class FriendRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    Long id;
    @Column(name = "user_id")
    Long user;
    @Column(name = "sub_id")
    Long sub;
    @Enumerated(EnumType.STRING)
    RequestStatus status;
}
