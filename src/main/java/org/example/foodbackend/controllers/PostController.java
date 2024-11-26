package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.InstructionStep;
import org.example.foodbackend.entities.Post;
import org.example.foodbackend.entities.dto.*;
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
    public PaginatedResponseDTO<PostDetailsResponseDTO> getRecommendPosts(
            @AuthenticationPrincipal Account user,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam EDaySession daySession) {
        return service.getAllRecommendPosts(user, page, size, daySession);
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
    public List<PostDetailsResponseDTO> getRecommendPostsBySession(
            @AuthenticationPrincipal Account user,
            @RequestParam EDaySession session) {
        return service.getRecommendPostsBySession(user, session);
    }

    @GetMapping("/list/by-kitchen")
    public List<PostDetailsResponseDTO> getRecommendPostsByKitchen(@AuthenticationPrincipal Account user) {
        return service.getRecommendPostsByKitchen(user);
    }

    @GetMapping("{id}/steps")
    public ResponseEntity<List<InstructionStep>> getPostSteps(@PathVariable Long id) {
        return service.getPostStep(id);
    }

    @PostMapping("{id}/cook")
    public ResponseEntity<?> cookPost(@AuthenticationPrincipal Account user, @PathVariable Long id, @RequestParam int quantity) {
        return service.cookPost(user, id, quantity);
    }

    @GetMapping("/cook")
    public ResponseEntity<PaginatedResponseDTO<PostHistoryDetailsDTO>> getCookedList(
            @AuthenticationPrincipal Account user,
            @RequestParam int page,
            @RequestParam int size) {
        return service.getListCooked(user, page, size);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> deleteById(@AuthenticationPrincipal Account user, @PathVariable Long id) {
        return service.deleteUserPost(user, id);
    }
}
