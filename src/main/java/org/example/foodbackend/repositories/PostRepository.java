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
    @Query("SELECT p FROM Post p WHERE p.is_standard = false ORDER BY p.published_time DESC")
    Page<Post> findAllByOrderByPublishedTimeDesc(Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.likedUsers u WHERE u.id = :userId AND p.user.id != :userId")
    Page<Post> findAllLikedPostsByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    Page<Post> getPostedPost(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.daySessions s WHERE s.name = :session AND p.is_standard = true")
    Page<Post> getPostsBySession(EDaySession session, Pageable pageable);

    @Query("""
                SELECT p FROM Post p
                WHERE NOT EXISTS (
                    SELECT pt FROM KitchenTool pt
                    JOIN p.tools t
                    WHERE t.id NOT IN :toolIds
                )
                AND NOT EXISTS (
                    SELECT ps FROM KitchenSpice ps
                    JOIN p.spices s
                    WHERE s.id NOT IN :spiceIds
                )
                AND NOT EXISTS (
                     SELECT pi FROM PostIngredient pi
                     JOIN pi.ingredient i
                     LEFT JOIN UserIngredient ui ON ui.ingredient.id = i.id AND ui.user.id = :userId 
                     WHERE pi.post.id = p.id
                     AND (ui.ingredient IS NULL OR pi.quantity > COALESCE(ui.quantity, 0)) 
                )
                AND p.is_standard = true
            """)
    Page<Post> getPostsByKitchen(
            Long userId,
            List<Long> toolIds,
            List<Long> spiceIds,
            Pageable pageable);
}
