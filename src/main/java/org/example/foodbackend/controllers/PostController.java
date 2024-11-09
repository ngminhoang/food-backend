package org.example.foodbackend.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.foodbackend.controllers.base.BaseController;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.Post;
import org.example.foodbackend.entities.dto.PostRequestDTO;
import org.example.foodbackend.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
