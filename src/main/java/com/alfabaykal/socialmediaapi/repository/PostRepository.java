package com.alfabaykal.socialmediaapi.repository;

import com.alfabaykal.socialmediaapi.model.Post;
import com.alfabaykal.socialmediaapi.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author, Pageable pageable);

    Page<Post> findByAuthorInOrderByCreatedAtDesc(List<User> authors, Pageable pageable);

    @EntityGraph(attributePaths = "author")
    Optional<Post> findWithAuthorById(Long id);
}
