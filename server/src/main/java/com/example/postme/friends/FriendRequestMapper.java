package com.example.postme.friends;


import com.example.postme.friends.dto.FriendRequestDto;
import com.example.postme.user.dto.UserShort;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FriendRequestMapper {
    public static FriendRequestDto toFriendRequestDto(FriendRequest friendRequest) {
        return FriendRequestDto.builder()
                .id(friendRequest.getId())
                .user(new UserShort(friendRequest.getUser()))
                .requestFromUser(new UserShort(friendRequest.getSub()))
                .status(friendRequest.getStatus())
                .build();
    }
}
