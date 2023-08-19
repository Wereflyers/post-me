package com.example.postme.friends.dto;

import com.example.postme.friends.RequestStatus;
import com.example.postme.user.dto.UserShort;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendRequestDto {
    Long id;
    UserShort user;
    UserShort requestFromUser;
    RequestStatus status;
}
