package com.example.postme.posts;

import com.example.postme.exception.ValidationException;
import com.example.postme.exception.WrongConditionException;
import com.example.postme.friends.FriendRequest;
import com.example.postme.friends.FriendRequestRepository;
import com.example.postme.posts.dto.NewPostDto;
import com.example.postme.posts.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Override
    public List<PostDto> getAllForUser(long userId, int from, int size) {
       //TODO validation from, size in controller
        return postRepository.findAllByCreatorOrderByPublishedOn(userId, PageRequest.of(from / size, size)).stream()
                .map(PostMapper::toPostDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostDto add(long userId, NewPostDto newPostDto) {
        Post post = postRepository.save(PostMapper.fromNewPostDto(newPostDto, userId));
        return PostMapper.toPostDto(post);
    }

    @Override
    public PostDto get(long postId, long userId) {
        return PostMapper.toPostDto(postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("Post with id=" + postId + " was not found")));
    }

    @Override
    public PostDto update(long userId, long postId, NewPostDto newPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("Post with id=" + postId + " was not found"));
        if (post.getCreator() != userId) {
            throw new WrongConditionException("You have no rights to update this post.");
        }
        if (newPostDto.getTitle() != null) post.setTitle(newPostDto.getTitle());
        if (newPostDto.getDescription() != null) post.setDescription(newPostDto.getDescription());
        return PostMapper.toPostDto(post);
    }

    @Override
    public void delete(long userId, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("Post with id=" + postId + " was not found"));
        if (post.getCreator() != userId) {
            throw new WrongConditionException("You have no rights to delete this post.");
        }
        postRepository.deleteById(postId);
    }

    @Override
    public List<PostDto> getAllFollowed(long userId, int from) {
        //TODO validation from in controller
        if (from < 0) {
            throw new ValidationException("Wrong parameters");
        }
        List<FriendRequest> followedUsers = friendRequestRepository.findAllBySub(userId, PageRequest.of(0 / 20, 20));
        List<Long> followedUsersIds = followedUsers.stream()
                .map(FriendRequest::getUser)
                .collect(Collectors.toList());
        return postRepository.findAllByCreatorInOrderByPublishedOnDesc(followedUsersIds, PageRequest.of(from / 10, 10)).stream()
                .map(PostMapper::toPostDto)
                .collect(Collectors.toList());
    }
}
