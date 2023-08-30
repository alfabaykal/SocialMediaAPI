package com.alfabaykal.socialmediaapi.service;

import com.alfabaykal.socialmediaapi.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> getAllPosts();

    List<Post> getPostsByAuthor(Long userId, Pageable pageable);

    Page<Post> getFeed(Long userId, Pageable pageable);

    Optional<Post> getPostById(Long postId);

    Post createPost(Post post);

    Post updatePost(Long postId, Post post);

    void deletePost(Long postId);

}
