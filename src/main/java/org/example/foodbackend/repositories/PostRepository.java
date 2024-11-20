package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.Post;
import org.example.foodbackend.entities.dto.KitchenIngredientRequestDTO;
import org.example.foodbackend.entities.enums.EDaySession;
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

    @Query("SELECT p FROM Post p JOIN p.likedUsers u WHERE u.id = :userId AND p.language = :lang AND p.user.id != :userId")
    Page<Post> findAllLikedPostsByUserId(Long userId, ELanguage lang, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    Page<Post> getPostedPost(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.daySessions s WHERE s.name = :session AND p.is_standard = true")
    Page<Post> getPostsBySession(EDaySession session, Pageable pageable);

    @Query("")
    Page<Post> getPostsByKitchen(Long toolIds, Long spiceIds, KitchenIngredientRequestDTO ngredientQuantity, Pageable pageable);
}
