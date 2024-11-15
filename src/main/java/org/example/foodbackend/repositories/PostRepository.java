package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Post;
import org.example.foodbackend.entities.enums.ELanguage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p ORDER BY p.published_time DESC")
    Page<Post> findAllByOrderByPublishedTimeDesc(Pageable pageable);
    @Query("SELECT p FROM Post p JOIN p.likedUsers u WHERE u.id = :userId AND p.language = :lang")
    Page<Post> findAllLikedPostsByUserId(Long userId, ELanguage lang, Pageable pageable);
}
