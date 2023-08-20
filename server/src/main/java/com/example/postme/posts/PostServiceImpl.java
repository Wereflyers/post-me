package com.example.postme.posts;

import com.example.postme.exception.WrongConditionException;
import com.example.postme.friends.FriendRequest;
import com.example.postme.friends.FriendRequestRepository;
import com.example.postme.friends.RequestStatus;
import com.example.postme.posts.dto.NewImageDto;
import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FriendRequestRepository friendRequestRepository;
    private final ImageServiceImpl imageService;

    @Override
    public List<PostDto> getAllForUser(String username, int from, int size) {
        return postRepository.findAllByCreatorOrderByPublishedOn(username, PageRequest.of(from / size, size)).stream()
                .map(this::createPostDtoWithImages)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PostDto add(String username, NewPostDto newPostDto) {
        Post post = postRepository.save(PostMapper.fromNewPostDto(newPostDto, username));
        if (newPostDto.getImages() != null) {
            for (NewImageDto imageDto : newPostDto.getImages()) {
                imageService.saveImage(imageDto, post.getId());
            }
        }
        return createPostDtoWithImages(post);
    }

    @Override
    public PostDto get(long postId, String username) {
        return createPostDtoWithImages(postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("Post with id=" + postId + " was not found")));
    }

    @Override
    @Transactional
    public PostDto update(String username, long postId, NewPostDto newPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("Post with id=" + postId + " was not found"));
        if (!Objects.equals(post.getCreator(), username)) {
            throw new WrongConditionException("You have no rights to update this post.");
        }
        if (newPostDto.getTitle() != null) post.setTitle(newPostDto.getTitle());
        if (newPostDto.getDescription() != null) post.setDescription(newPostDto.getDescription());
        if (newPostDto.getImages() != null) {
            imageService.deleteImages(postId);
            for (NewImageDto imageDto: newPostDto.getImages()) {
                imageService.saveImage(imageDto, postId);
            }
        }
        return createPostDtoWithImages(post);
    }

    @Override
    @Transactional
    public void delete(String username, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("Post with id=" + postId + " was not found"));
        if (!Objects.equals(post.getCreator(), username)) {
            throw new WrongConditionException("You have no rights to delete this post.");
        }
        imageService.deleteImages(postId);
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> getAllFollowed(String username, int from) {
        List<FriendRequest> followedUsers = friendRequestRepository.findAllBySub(username, PageRequest.of(0 / 20, 20));
        List<String> followedUsersNames = followedUsers.stream()
                .map(FriendRequest::getUser)
                .collect(Collectors.toList());
        friendRequestRepository.findAllByUserAndStatus(username, RequestStatus.ACCEPTED).stream()
                .map(FriendRequest::getSub)
                .forEach(followedUsersNames::add);
        return postRepository.findAllByCreatorInOrderByPublishedOnDesc(followedUsersNames, PageRequest.of(from / 10, 10)).stream()
                .map(this::createPostDtoWithImages)
                .collect(Collectors.toList());
    }

    private PostDto createPostDtoWithImages(Post post) {
        return PostMapper.toPostDto(post, imageService.getImage(post.getId()));
    }
}
