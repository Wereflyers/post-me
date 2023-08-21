package com.example.postme.friends;

import com.example.postme.exception.ValidationException;
import com.example.postme.exception.WrongConditionException;
import com.example.postme.friends.dto.FriendRequestDto;
import com.example.postme.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public FriendRequestDto add(String username, String followedName) {
        if (userRepository.findByName(followedName).isEmpty()) {
            throw new NullPointerException("User not found");
        }
        FriendRequest friendRequest = FriendRequest.builder()
                .user(followedName)
                .sub(username)
                .status(RequestStatus.WAITING)
                .build();
        return FriendRequestMapper.toFriendRequestDto(friendRequestRepository.save(friendRequest));
    }

    @Override
    public FriendRequestDto get(String username, long requestId) {
        return FriendRequestMapper.toFriendRequestDto(friendRequestRepository.findByIdAndUserOrSub(requestId, username, username)
                .orElseThrow(() -> new NullPointerException("Request not found")));
    }

    @Override
    public List<FriendRequestDto> getRequests(String username, int from) {
        if (from < 0) {
            throw new ValidationException("Wrong parameters");
        }
        return friendRequestRepository.findAllBySub(username, PageRequest.of(from / 10, 10)).stream()
                .map(FriendRequestMapper::toFriendRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FriendRequestDto apply(String username, long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new NullPointerException("Request not found"));
        if (!Objects.equals(friendRequest.getUser(), username) || friendRequest.getStatus() != RequestStatus.WAITING) {
            throw new NullPointerException("Request not found");
        }
        friendRequest.setStatus(RequestStatus.ACCEPTED);
        return FriendRequestMapper.toFriendRequestDto(friendRequestRepository.save(friendRequest));
    }

    @Override
    @Transactional
    public FriendRequestDto cancel(String username, long requestId) {
        FriendRequest friendRequest = friendRequestRepository.findById(requestId)
                .orElseThrow(() -> new NullPointerException("Request not found"));
        if (!Objects.equals(friendRequest.getUser(), username) || friendRequest.getStatus() != RequestStatus.WAITING) {
            throw new NullPointerException("Request not found");
        }
        friendRequest.setStatus(RequestStatus.CANCELED);
        return FriendRequestMapper.toFriendRequestDto(friendRequestRepository.save(friendRequest));
    }

    @Override
    @Transactional
    public void unsub(String username, String followedName) {
        if (userRepository.findByName(followedName).isEmpty()) {
            throw new NullPointerException("User not found");
        }
        Optional<FriendRequest> friendRequest = friendRequestRepository.findByUserAndSub(followedName, username);
        Optional<FriendRequest> friendship = friendRequestRepository.findByUserAndSubAndStatus(username, followedName,
                RequestStatus.ACCEPTED);
        if (friendRequest.isEmpty() && friendship.isEmpty()) throw new WrongConditionException("Please subscribe");

        friendship.ifPresent(request -> friendRequestRepository.deleteById(request.getId()));
        friendRequest.ifPresent(request -> friendRequestRepository.deleteById(request.getId()));
        if (friendship.isPresent() || friendRequest.get().getStatus() == RequestStatus.ACCEPTED) {
            friendRequestRepository.save(FriendRequest.builder()
                    .user(username)
                    .sub(followedName)
                    .status(RequestStatus.CANCELED)
                    .build());
        }
    }
}
