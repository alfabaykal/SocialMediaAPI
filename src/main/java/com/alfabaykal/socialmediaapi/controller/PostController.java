package com.alfabaykal.socialmediaapi.controller;

import com.alfabaykal.socialmediaapi.dto.PostDeleteDto;
import com.alfabaykal.socialmediaapi.dto.PostDto;
import com.alfabaykal.socialmediaapi.exception.*;
import com.alfabaykal.socialmediaapi.facade.PostFacade;
import com.alfabaykal.socialmediaapi.security.JwtUtil;
import com.alfabaykal.socialmediaapi.util.BindingResultConverter;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Посты", description = "Методы для управлениями постами")
@RestController
@RequestMapping("/v1/posts")
public class PostController {

    private final PostFacade postFacade;
    private final BindingResultConverter bindingResultConverter;
    private final JwtUtil jwtUtil;

    public PostController(PostFacade postFacade,
                          BindingResultConverter bindingResultConverter,
                          JwtUtil jwtUtil) {
        this.postFacade = postFacade;
        this.bindingResultConverter = bindingResultConverter;
        this.jwtUtil = jwtUtil;
    }

    @Operation(summary = "Получение всех постов")
    @GetMapping
    public List<PostDto> getAllPosts() {
        return postFacade.getAllPosts();
    }

    @Operation(summary = "Просмотр постов за авторством определенного пользователя")
    @GetMapping("/activity/{userId}")
    public List<PostDto> getPostsByAuthor(@Parameter(description = "Идентификатор автора")
                                          @PathVariable Long userId,
                                          @PageableDefault(size = 5, sort = "createdAt",
                                                  direction = Sort.Direction.DESC) Pageable pageable) {
        return postFacade.getPostsByAuthor(userId, pageable);
    }

    @Operation(summary = "Просмотр постов авторов, на которых подписан")
    @GetMapping("/feed")
    public Page<PostDto> myActivityFeed(@Parameter(description = "Bearer header with JWT")
                                        @RequestHeader("Authorization") String jwtHeader,
                                        @PageableDefault(size = 5, sort = "createdAt",
                                                direction = Sort.Direction.DESC) Pageable pageable) {
        return postFacade.getFeed(jwtUtil.getUserIdByJwtHeader(jwtHeader), pageable);
    }

    @Operation(summary = "Получение поста по идентификатору")
    @GetMapping("/{postId}")
    public PostDto getPostById(@Parameter(description = "Идентификатор поста")
                               @PathVariable Long postId) {
        return postFacade.getPostDtoById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
    }

    @Operation(summary = "Создание поста")
    @PostMapping
    public PostDto createPost(@Parameter(description = "Bearer header with JWT")
                              @RequestHeader("Authorization") String jwtHeader,
                              @RequestBody PostDto postDto,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new PostNotCreatedException("Post creating failed: "
                    + bindingResultConverter.convertBindingResultToString(bindingResult));
        }

        Long authorId = jwtUtil.getUserIdByJwtHeader(jwtHeader);

        postDto.setAuthor(postFacade.getUserById(authorId)
                .orElseThrow(() -> new UserNotFoundException(authorId)));

        return postFacade.createPost(postDto);
    }

    @Operation(summary = "Редактирование поста")
    @PatchMapping("/{postId}")
    public PostDto updateMyPost(@Parameter(description = "Идентификатор поста")
                                @PathVariable Long postId,
                                @Parameter(description = "Bearer header with JWT")
                                @RequestHeader("Authorization") String jwtHeader,
                                @RequestBody PostDto postDto,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new PostNotCreatedException("Post updated failed: "
                    + bindingResultConverter.convertBindingResultToString(bindingResult));
        }

        PostDto updatedPostDto = postFacade.updatePost(postId, postDto);
        Long authorId = jwtUtil.getUserIdByJwtHeader(jwtHeader);
        if (updatedPostDto.getAuthor().equals(postFacade.getUserById(authorId)
                .orElseThrow(() -> new UserNotFoundException(authorId)))) {
            return updatedPostDto;
        } else {
            throw new NotYourPostException("You can't update other people's posts");
        }
    }

    @Hidden
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{postId}")
    public PostDto updatePost(@Parameter(description = "Идентификатор поста")
                              @PathVariable Long postId,
                              @RequestBody PostDto postDto) {
        return postFacade.updatePost(postId, postDto);
    }

    @Operation(summary = "Удаление поста")
    @DeleteMapping()
    public void deleteMyPost(@Parameter(description = "Bearer header with JWT")
                             @RequestHeader("Authorization") String jwtHeader,
                             @RequestBody PostDeleteDto postDeleteDto) {
        if (postFacade.getPostDtoById(postDeleteDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(postDeleteDto.getPostId()))
                .getAuthor().getId()
                .equals(jwtUtil.getUserIdByJwtHeader(jwtHeader)))
            postFacade.deletePost(postDeleteDto.getPostId());
        else
            throw new NotYourPostException("You can't delete other people's posts");
    }

    @Hidden
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postFacade.deletePost(postId);
    }
}
