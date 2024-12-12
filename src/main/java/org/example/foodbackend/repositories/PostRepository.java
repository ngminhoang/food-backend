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
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.is_standard = false ORDER BY p.published_time DESC")
    Page<Post> findAllByOrderByPublishedTimeDesc(Pageable pageable);

    @Query("SELECT p FROM Post p JOIN p.likedUsers u WHERE u.id = :userId AND p.user.id != :userId")
    Page<Post> findAllLikedPostsByUserId(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    Page<Post> getPostedPost(Long userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId AND p.id = :postId")
    Optional<Post> getPostedPostByPostId(Long userId, Long postId);

    @Query("SELECT p FROM Post p JOIN p.daySessions s LEFT JOIN p.histories h WHERE s.name = :session AND p.is_standard = true AND ((h.cookedTime < :timeLimit and h.user.id = :userId) OR h.cookedTime IS NULL)")
    Page<Post> getPostsBySession(Long userId, EDaySession session, LocalDateTime timeLimit, Pageable pageable);

    @Query("""
                SELECT p FROM Post p
                LEFT JOIN p.histories h
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
                AND p.language = :lang
                AND ((h.cookedTime < :timeLimit AND h.user.id = :userId) OR h.cookedTime IS NULL)
            """)
    Page<Post> getPostsByKitchen(
            Long userId,
            List<Long> toolIds,
            List<Long> spiceIds,
            ELanguage lang,
            LocalDateTime timeLimit,
            Pageable pageable
    );

    @Query("""
                  SELECT p FROM Post p
                  JOIN p.daySessions s
                  LEFT JOIN p.likedUsers lu
                  LEFT JOIN p.histories h
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
                  AND p.is_standard = false
                  AND p.language = :lang
                  AND s.name = :session
                  AND p.user.id != :userId
                  AND ((h.cookedTime < :timeLimit AND h.user.id = :userId) OR h.cookedTime IS NULL)
                  GROUP BY p.id
                  ORDER BY COUNT(lu) DESC, p.published_time DESC
            """)
    List<Post> getRecommendedPosts(
            Long userId,
            List<Long> toolIds,
            List<Long> spiceIds,
            ELanguage lang,
            EDaySession session,
            LocalDateTime timeLimit);

    @Query("""
            SELECT p FROM Post p
            JOIN p.daySessions s
            LEFT JOIN p.likedUsers lu
            LEFT JOIN p.histories h
            WHERE p.language = :lang
            AND p.is_standard = false
            AND s.name =:session
            AND p.user.id != :userId
            AND ((h.cookedTime < :timeLimit AND h.user.id = :userId) OR h.cookedTime IS NULL)
            GROUP BY p.id
            ORDER BY COUNT(lu) DESC""")
    List<Post> getListPostByLikesDesc(Long userId, ELanguage lang, EDaySession session, LocalDateTime timeLimit);

    @Query("""
                  SELECT p FROM Post p
                  JOIN p.daySessions s
                  LEFT JOIN p.histories h
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
                  AND p.language = :lang
                  AND s.name = :session
                  AND ((h.cookedTime < :timeLimit AND h.user.id = :userId) OR h.cookedTime IS NULL)
            """)
    List<Post> getBestDish(Long userId,
                           List<Long> toolIds,
                           List<Long> spiceIds,
                           ELanguage lang,
                           EDaySession session,
                           LocalDateTime timeLimit);
    @Query("""
                  SELECT p FROM Post p
                  JOIN p.daySessions s
                  LEFT JOIN p.histories h
                  WHERE p.is_standard = true
                  AND p.language = :lang
                  AND s.name = :session
                  AND ((h.cookedTime < :timeLimit AND h.user.id = :userId) OR h.cookedTime IS NULL)
            """)
    List<Post> getBestDishFallback(Long userId,
                                   ELanguage lang,
                                   EDaySession session,
                                   LocalDateTime timeLimit);
    @Query("SELECT p FROM Post p " +
            "LEFT JOIN p.daySessions s " +
            "LEFT JOIN p.post_ingredients pi " +
            "LEFT JOIN pi.ingredient i " +
            "LEFT JOIN p.likedUsers lu " +
            "WHERE" +
            "(LOWER(p.dish_name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(p.is_standard = :isStandard) AND" +
            "(:sessionIds IS NULL OR s.id IN :sessionIds) AND " +
            "(:ingredientIds IS NULL OR i.id IN :ingredientIds)" +
            "GROUP BY p.id " +
            "ORDER BY COUNT(lu) ASC")
    Page<Post> searchRecipesSortByLikesASC(
            String name,
            Boolean isStandard,
            List<Long> sessionIds,
            List<Long> ingredientIds,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p " +
            "JOIN p.daySessions s " +
            "LEFT JOIN p.post_ingredients pi " +
            "LEFT JOIN pi.ingredient i " +
            "LEFT JOIN p.likedUsers lu " +
            "WHERE" +
            "(LOWER(p.dish_name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(p.is_standard = :isStandard) AND" +
            "(:sessionIds IS NULL OR s.id IN :sessionIds) AND " +
            "(:ingredientIds IS NULL OR i.id IN :ingredientIds)" +
            "GROUP BY p.id " +
            "ORDER BY COUNT(lu) DESC")
    Page<Post> searchRecipesSortByLikesDESC(
            String name,
            Boolean isStandard,
            List<Long> sessionIds,
            List<Long> ingredientIds,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p " +
            "JOIN p.daySessions s " +
            "LEFT JOIN p.post_ingredients pi " +
            "LEFT JOIN pi.ingredient i " +
            "WHERE " +
            "(LOWER(p.dish_name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "p.is_standard = :isStandard AND " +
            "(:sessionIds IS NULL OR s.id IN :sessionIds) AND " +
            "(:ingredientIds IS NULL OR i.id IN :ingredientIds)" +
            "GROUP BY p.id " +
            "ORDER BY p.published_time ASC")
    Page<Post> searchRecipesSortByPublishTimeASC(
            String name,
            Boolean isStandard,
            List<Long> sessionIds,
            List<Long> ingredientIds,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p " +
            "JOIN p.daySessions s " +
            "LEFT JOIN p.post_ingredients pi " +
            "LEFT JOIN pi.ingredient i " +
            "WHERE " +
            "(LOWER(p.dish_name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "p.is_standard = :isStandard AND " +
            "(:sessionIds IS NULL OR s.id IN :sessionIds) AND " +
            "(:ingredientIds IS NULL OR i.id IN :ingredientIds)" +
            "GROUP BY p.id " +
            "ORDER BY p.published_time DESC")
    Page<Post> searchRecipesSortByPublishTimeDESC(
            String name,
            Boolean isStandard,
            List<Long> sessionIds,
            List<Long> ingredientIds,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p " +
            "JOIN p.daySessions s " +
            "LEFT JOIN p.post_ingredients pi " +
            "LEFT JOIN pi.ingredient i " +
            "WHERE " +
            "(LOWER(p.dish_name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "p.is_standard = :isStandard AND " +
            "(:sessionIds IS NULL OR s.id IN :sessionIds) AND " +
            "(:ingredientIds IS NULL OR i.id IN :ingredientIds)" +
            "GROUP BY p.id " +
            "ORDER BY p.duration ASC")
    Page<Post> searchRecipesSortByDurationASC(
            String name,
            Boolean isStandard,
            List<Long> sessionIds,
            List<Long> ingredientIds,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p " +
            "JOIN p.daySessions s " +
            "LEFT JOIN p.post_ingredients pi " +
            "LEFT JOIN pi.ingredient i " +
            "WHERE " +
            "(LOWER(p.dish_name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "p.is_standard = :isStandard AND " +
            "(:sessionIds IS NULL OR s.id IN :sessionIds) AND " +
            "(:ingredientIds IS NULL OR i.id IN :ingredientIds)" +
            "GROUP BY p.id " +
            "ORDER BY p.duration DESC")
    Page<Post> searchRecipesSortByDurationDESC(
            String name,
            Boolean isStandard,
            List<Long> sessionIds,
            List<Long> ingredientIds,
            Pageable pageable
    );
}
