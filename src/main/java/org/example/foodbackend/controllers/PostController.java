package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.Post;
import org.example.foodbackend.entities.dto.KitchenIngredientPostDTO;
import org.example.foodbackend.entities.dto.PostDetailsResponseDTO;
import org.example.foodbackend.entities.dto.PostRequestDTO;
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
    public List<PostDetailsResponseDTO> getRecentPosts(@AuthenticationPrincipal Account user) {
        return service.getAllRecentPost(user);
    }

    @GetMapping("post/recommend")
    public List<PostDetailsResponseDTO> getRecommendPosts(@AuthenticationPrincipal Account user) {
        return service.getAllRecommendPosts(user);
    }

    @PostMapping("post/like/{id}")
    public ResponseEntity<?> likePost(@AuthenticationPrincipal Account user, @PathVariable Long id) {
        return service.likePost(user, id);
    }

}
