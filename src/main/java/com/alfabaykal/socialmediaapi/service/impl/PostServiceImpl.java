package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.dto.PostDto;
import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.dto.UserRelationshipDto;
import com.alfabaykal.socialmediaapi.exception.PostNotFoundException;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.repository.PostRepository;
import com.alfabaykal.socialmediaapi.service.PostService;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final EntityDtoConverter entityDtoConverter;
    private final UserServiceImpl userServiceImpl;

    public PostServiceImpl(PostRepository postRepository, EntityDtoConverter entityDtoConverter, UserServiceImpl userServiceImpl) {
        this.postRepository = postRepository;
        this.entityDtoConverter = entityDtoConverter;
        this.userServiceImpl = userServiceImpl;
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(entityDtoConverter::convertPostToPostDto).toList();
    }

    public List<PostDto> getPostsByAuthor(Long userId, Pageable pageable) {
        return postRepository.findByAuthor(entityDtoConverter.convertUserDtoToUser(userServiceImpl.getUserById(userId)
                        .orElseThrow(() -> new UserNotFoundException(userId))), pageable).stream()
                .map(entityDtoConverter::convertPostToPostDto).toList();
    }

    public Page<PostDto> getFeed(Long userId, Pageable pageable) {
        UserRelationshipDto userRelationshipDto = userServiceImpl.getUserByIdWithSubscriptions(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        List<UserDto> subscriptions = entityDtoConverter
                .convertUserRelationshipDtoToUser(userRelationshipDto).getSubscriptions().stream()
                .map(entityDtoConverter::convertUserToUserDto).toList();
        return postRepository.findByAuthorInOrderByCreatedAtDesc(subscriptions.stream()
                        .map(entityDtoConverter::convertUserDtoToUser).toList(), pageable)
                .map(entityDtoConverter::convertPostToPostDto);
    }

    public Optional<PostDto> getPostDtoById(Long postId) {
        return postRepository.findWithAuthorById(postId)
                .map(entityDtoConverter::convertPostToPostDto);
    }

    @Transactional
    public PostDto createPost(PostDto postDto) {
        return entityDtoConverter
                .convertPostToPostDto(postRepository.save(entityDtoConverter.convertPostDtoToPost(postDto)));
    }

    @Transactional
    public PostDto updatePost(Long postId, PostDto postDto) {
        if (postRepository.existsById(postId)) {
            postDto.setId(postId);
            return entityDtoConverter
                    .convertPostToPostDto(postRepository.save(entityDtoConverter.convertPostDtoToPost(postDto)));
        } else {
            throw new PostNotFoundException(postId);
        }
    }

    @Transactional
    public void deletePost(Long postId) {
        if (postRepository.existsById(postId))
            postRepository.deleteById(postId);
    }

}
