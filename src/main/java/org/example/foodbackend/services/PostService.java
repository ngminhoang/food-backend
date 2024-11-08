package org.example.foodbackend.services;

import org.example.foodbackend.entities.Post;
import org.example.foodbackend.repositories.PostRepository;
import org.example.foodbackend.services.base.BaseService;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PostService extends BaseServiceImpl<Post, Long, PostRepository> implements BaseService<Post, Long> {
    public PostService(PostRepository rootRepository) {
        super(rootRepository);
    }
}
