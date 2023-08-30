package com.alfabaykal.socialmediaapi.facade.impl;

import com.alfabaykal.socialmediaapi.dto.PostDto;
import com.alfabaykal.socialmediaapi.dto.UserDto;
import com.alfabaykal.socialmediaapi.facade.PostFacade;
import com.alfabaykal.socialmediaapi.service.PostService;
import com.alfabaykal.socialmediaapi.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostFacadeImpl implements PostFacade {

    private final PostService postService;
    private final UserService userService;

    public PostFacadeImpl(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts();
    }

    @Override
    public List<PostDto> getPostsByAuthor(Long userId, Pageable pageable) {
        return postService.getPostsByAuthor(userId, pageable);
    }

    @Override
    public Page<PostDto> getFeed(Long userId, Pageable pageable) {
        return postService.getFeed(userId, pageable);
    }

    @Override
    public Optional<PostDto> getPostDtoById(Long postId) {
        return postService.getPostDtoById(postId);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        return postService.createPost(postDto);
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) {
        return postService.updatePost(postId, postDto);
    }

    @Override
    public void deletePost(Long postId) {
        postService.deletePost(postId);
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userService.getUserById(id);
    }
}
