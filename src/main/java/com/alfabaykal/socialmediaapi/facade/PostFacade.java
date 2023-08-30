package com.alfabaykal.socialmediaapi.facade;

import com.alfabaykal.socialmediaapi.dto.PostDto;
import com.alfabaykal.socialmediaapi.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostFacade {

    List<PostDto> getAllPosts();

    List<PostDto> getPostsByAuthor(Long userId, Pageable pageable);

    Page<PostDto> getFeed(Long userId, Pageable pageable);

    Optional<PostDto> getPostDtoById(Long postId);

    PostDto createPost(PostDto postDto);

    PostDto updatePost(Long postId, PostDto postDto);

    void deletePost(Long postId);

    Optional<UserDto> getUserById(Long id);

}
