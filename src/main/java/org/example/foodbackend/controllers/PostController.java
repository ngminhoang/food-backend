package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.Post;
import org.example.foodbackend.entities.dto.KitchenIngredientPostDTO;
import org.example.foodbackend.entities.dto.PaginatedResponseDTO;
import org.example.foodbackend.entities.dto.PostDetailsResponseDTO;
import org.example.foodbackend.entities.dto.PostRequestDTO;
import org.example.foodbackend.entities.enums.EDaySession;
import org.example.foodbackend.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@Tag(name = "FooFi - Post")
public class PostController extends BaseController<Post, Long, PostService> {
    public PostController(PostService service) {
        super(service);
    }

    @PostMapping("/post/create")
    public ResponseEntity<?> createNewPost(@AuthenticationPrincipal Account user, @RequestBody PostRequestDTO postRequestDTO) {
        return service.addPost(user, postRequestDTO);
    }

    @GetMapping("/post/recent")
    public PaginatedResponseDTO<PostDetailsResponseDTO> getRecentPosts(@AuthenticationPrincipal Account user, @RequestParam int page, @RequestParam int size) {
        return service.getAllRecentPost(user, page, size);
    }

    @GetMapping("post/recommend")
    public List<PostDetailsResponseDTO> getRecommendPosts(@AuthenticationPrincipal Account user) {
        return service.getAllRecommendPosts(user);
    }

    @PostMapping("post/like/{id}")
    public ResponseEntity<?> likePost(@AuthenticationPrincipal Account user, @PathVariable Long id) {
        return service.likePost(user, id);
    }

    @GetMapping("post/liked/list")
    public PaginatedResponseDTO<PostDetailsResponseDTO> getLikedPosts(@AuthenticationPrincipal Account user, @RequestParam int page, @RequestParam int size) {
        return service.getListPostsLiked(user, page, size);
    }

    @GetMapping("post/posted/list")
    public PaginatedResponseDTO<PostDetailsResponseDTO> getUserPostedPosts(@AuthenticationPrincipal Account user, @RequestParam int page, @RequestParam int size) {
        return service.getUsersPostedPosts(user, page, size);
    }

    @GetMapping("/list/by-session")
    public PaginatedResponseDTO<PostDetailsResponseDTO> getRecommendPostsBySession(
            @AuthenticationPrincipal Account user,
            @RequestParam EDaySession session) {
        return service.getRecommendPostsBySession(user, session);
    }

    @GetMapping("/list/by-kitchen")
    public PaginatedResponseDTO<PostDetailsResponseDTO> getRecommendPostsByKitchen(@AuthenticationPrincipal Account user) {
        return service.getRecommendPostsByKitchen(user);
    }
}
