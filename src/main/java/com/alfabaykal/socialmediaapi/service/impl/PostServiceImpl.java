package com.alfabaykal.socialmediaapi.service.impl;

import com.alfabaykal.socialmediaapi.exception.PostNotFoundException;
import com.alfabaykal.socialmediaapi.exception.UserNotFoundException;
import com.alfabaykal.socialmediaapi.model.Post;
import com.alfabaykal.socialmediaapi.model.User;
import com.alfabaykal.socialmediaapi.repository.PostRepository;
import com.alfabaykal.socialmediaapi.service.PostService;
import com.alfabaykal.socialmediaapi.service.UserService;
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
    private final UserService userService;

    public PostServiceImpl(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public List<Post> getPostsByAuthor(Long userId, Pageable pageable) {
        return postRepository.findByAuthor(userService.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId)), pageable);
    }

    public Page<Post> getFeed(Long userId, Pageable pageable) {
        User user = userService.getUserByIdWithSubscriptions(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<User> subscriptions = user.getSubscriptions().stream().toList();

        return postRepository.findByAuthorInOrderByCreatedAtDesc(subscriptions, pageable);

    }

    public Optional<Post> getPostById(Long postId) {
        return postRepository.findWithAuthorById(postId);
    }

    @Transactional
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public Post updatePost(Long postId, Post post) {
        if (postRepository.existsById(postId)) {
            post.setId(postId);
            return postRepository.save(post);
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
