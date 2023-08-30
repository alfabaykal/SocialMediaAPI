package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.dto.PostDto;
import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.facade.PostFacade;
import com.alfabaykal.socialmediaapi.service.PostService;
import com.alfabaykal.socialmediaapi.service.UserService;
import com.alfabaykal.socialmediaapi.util.EntityDtoConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostFacadeImpl implements PostFacade {

    private final PostService postService;
    private final UserService userService;
    private final EntityDtoConverter entityDtoConverter;

    public PostFacadeImpl(PostService postService, UserService userService, EntityDtoConverter entityDtoConverter) {
        this.postService = postService;
        this.userService = userService;
        this.entityDtoConverter = entityDtoConverter;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts().stream()
                .map(entityDtoConverter::convertPostToPostDto).toList();
    }

    @Override
    public List<PostDto> getPostsByAuthor(Long userId, Pageable pageable) {
        return postService.getPostsByAuthor(userId, pageable).stream()
                .map(entityDtoConverter::convertPostToPostDto).toList();
    }

    @Override
    public Page<PostDto> getFeed(Long userId, Pageable pageable) {
        return postService.getFeed(userId, pageable)
                .map(entityDtoConverter::convertPostToPostDto);
    }

    @Override
    public Optional<PostDto> getPostDtoById(Long postId) {
        return postService.getPostById(postId)
                .map(entityDtoConverter::convertPostToPostDto);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        return entityDtoConverter
                .convertPostToPostDto(postService.createPost(entityDtoConverter.convertPostDtoToPost(postDto)));
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        return entityDtoConverter
                .convertPostToPostDto(postService.updatePost(postId, entityDtoConverter.convertPostDtoToPost(postDto)));
    }

    @Override
    public void deletePost(Long postId) {
        postService.deletePost(postId);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userService.getUserById(id).map(entityDtoConverter::convertUserToUserDto);
    }
}
