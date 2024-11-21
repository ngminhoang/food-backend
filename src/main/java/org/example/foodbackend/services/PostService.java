package org.example.foodbackend.services;

import org.aspectj.apache.bcel.generic.Instruction;
import org.example.foodbackend.entities.*;
import org.example.foodbackend.entities.dto.*;
import org.example.foodbackend.entities.enums.EDaySession;
import org.example.foodbackend.entities.enums.ELanguage;
import org.example.foodbackend.repositories.*;
import org.example.foodbackend.services.base.BaseService;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PostService extends BaseServiceImpl<Post, Long, PostRepository> implements BaseService<Post, Long> {
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final InstructionStepRepository instructionStepRepository;
    @Autowired
    private final KitchenToolRepository kitchenToolRepository;
    @Autowired
    private final KitchenSpiceRepository kitchenSpiceRepository;
    @Autowired
    private final KitchenIngredientRepository kitchenIngredientRepository;
    @Autowired
    private final PostIngredientRepository postIngredientRepository;
    @Autowired
    private UserIngredientRepository userIngredientRepository;

    public PostService(PostRepository rootRepository, AccountRepository accountRepository, InstructionStepRepository instructionStepRepository, KitchenToolRepository kitchenToolRepository, KitchenSpiceRepository kitchenSpiceRepository, KitchenIngredientRepository kitchenIngredientRepository, PostIngredientRepository postIngredientRepository, UserIngredientRepository userIngredientRepository) {
        super(rootRepository);
        this.accountRepository = accountRepository;
        this.instructionStepRepository = instructionStepRepository;
        this.kitchenToolRepository = kitchenToolRepository;
        this.kitchenSpiceRepository = kitchenSpiceRepository;
        this.kitchenIngredientRepository = kitchenIngredientRepository;
        this.postIngredientRepository = postIngredientRepository;
        this.userIngredientRepository = userIngredientRepository;
    }

    public ResponseEntity<?> addPost(Account user, PostRequestDTO postRequestDTO) {
        try {
            Account account = accountRepository.findById(user.getId()).get();
            List<KitchenTool> listTools = kitchenToolRepository.findAllById(postRequestDTO.getTools());
            List<KitchenSpice> listSpices = kitchenSpiceRepository.findAllById(postRequestDTO.getSpices());

            Post post = Post.builder()
                    .dish_name(postRequestDTO.getDish_name())
                    .dish_img_url(postRequestDTO.getDish_img_url())
                    .description(postRequestDTO.getDescription())
                    .language(postRequestDTO.getLanguage())
                    .duration(postRequestDTO.getDuration())
                    .daySessions(postRequestDTO.getSessions())
                    .user(account)
                    .tools(listTools)
                    .spices(listSpices)
                    .is_standard(false)
                    .build();
            Post postReturn = rootRepository.save(post);
            for (KitchenIngredientRequestDTO ingredient : postRequestDTO.getIngredients()) {
                KitchenIngredient kitchenIngredient = kitchenIngredientRepository
                        .findById(ingredient.getId())
                        .orElseThrow(ChangeSetPersister.NotFoundException::new);
                PostIngredient postIngredient = PostIngredient.builder()
                        .post(postReturn)
                        .ingredient(kitchenIngredient)
                        .quantity(ingredient.getQuantity())
                        .build();
                postIngredientRepository.save(postIngredient);
            }
            for (InstructionStep step : postRequestDTO.getSteps()) {
                InstructionStep instructionStep = InstructionStep.builder()
                        .step(step.getStep())
                        .title(step.getTitle())
                        .description(step.getDescription())
                        .img_url(step.getImg_url())
                        .duration(step.getDuration())
                        .post(postReturn)
                        .build();
                instructionStepRepository.save(instructionStep);
            }
            return ResponseEntity.ok(post);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private PaginatedResponseDTO<PostDetailsResponseDTO> convertToPostDetailDTO(Account user, Page<Post> postsPage) {
        List<Post> posts = postsPage.getContent();
        List<Long> userToolIds = user.getTools().stream().map(item -> item.getId()).toList();
        List<Long> userSpiceIds = user.getSpices().stream().map(item -> item.getId()).toList();
        List<UserIngredient> userIngredients = userIngredientRepository.findByUser(user);
        List<KitchenIngredientRequestDTO> kitchenIngredientIds = userIngredients.stream().map(item -> {
                KitchenIngredient ingredient = item.getIngredient();
                return KitchenIngredientRequestDTO.builder()
                        .id(ingredient.getId())
                        .quantity(item.getQuantity())
                        .build();
        }).toList();
        List<PostDetailsResponseDTO> dtoList = posts.stream().map(post -> {
            //display user info
            UserInfoDTO userInfoDTO = UserInfoDTO.builder()
                    .id(post.getUser().getId())
                    .mail(post.getUser().getMail())
                    .name(post.getUser().getName())
                    .avatar_url(post.getUser().getAvatar_url())
                    .language(post.getUser().getLanguage())
                    .build();
            //likes
            int likes = post.getLikedUsers().size();
            boolean isLiked = post.getLikedUsers().contains(user);
            //check tool
            List<ToolCheckDTO> toolCheckDTOS = post.getTools().stream().map(tool -> ToolCheckDTO.builder()
                    .isAvailable(userToolIds.contains(tool.getId()))
                    .id(tool.getId())
                    .name_en(tool.getName_en())
                    .name_vi(tool.getName_vi())
                    .img_url(tool.getImg_url())
                    .build()).toList();
            //check spice
            List<SpiceCheckDTO> spiceCheckDTOS = post.getSpices().stream().map(spice ->
                    SpiceCheckDTO.builder()
                            .id(spice.getId())
                            .name_en(spice.getName_en())
                            .name_vi(spice.getName_vi())
                            .img_url(spice.getImg_url())
                            .isAvailable(userSpiceIds.contains(spice.getId())).build()).toList();
            //get list ingredients for post
            List<PostIngredient> postIngredients = post.getPost_ingredients();
            List<IngredientCheckDTO> ingredientPostDTOS = postIngredients.stream().map(postIngredient -> {
                KitchenIngredient ingredient = postIngredient.getIngredient();
                KitchenIngredientRequestDTO ingredientCheck = KitchenIngredientRequestDTO.builder()
                        .id(ingredient.getId())
                        .quantity(postIngredient.getQuantity())
                        .build();

                //to do: check issue this line
                boolean isAvailable = kitchenIngredientIds.contains(ingredient.getId());
                return IngredientCheckDTO.builder()
                        .id(ingredient.getId())
                        .name_en(ingredient.getName_en())
                        .name_vi(ingredient.getName_vi())
                        .unit(ingredient.getUnit())
                        .img_url(ingredient.getImg_url())
                        .quantity(postIngredient.getQuantity())
                        .isAvailable(isAvailable)
                        .build();
            }).toList();
            //return result
            return PostDetailsResponseDTO.builder()
                    .id(post.getId())
                    .dish_name(post.getDish_name())
                    .dish_img_url(post.getDish_img_url())
                    .description(post.getDescription())
                    .duration(post.getDuration())
                    .language(post.getLanguage())
                    .published_time(post.getPublished_time())
                    .user(userInfoDTO)
                    .tools(toolCheckDTOS)
                    .spices(spiceCheckDTOS)
                    .ingredients(ingredientPostDTOS)
                    .likes(likes)
                    .is_liked(isLiked)
                    .build();
        }).toList();
        return PaginatedResponseDTO.<PostDetailsResponseDTO>builder()
                .data(dtoList)
                .totalPages(postsPage.getTotalPages())
                .totalItems(postsPage.getTotalElements())
                .currentPage(postsPage.getNumber())
                .build();
    }

    public PaginatedResponseDTO<PostDetailsResponseDTO> getAllRecentPost(Account user, int page, int size) {
        Account userFound = accountRepository.findById(user.getId()).get();
        Pageable pageable = PageRequest.of(page, size);
        return convertToPostDetailDTO(userFound, rootRepository.findAllByOrderByPublishedTimeDesc(pageable));
    }

    public List<PostDetailsResponseDTO> getAllRecommendPosts(Account user) {
        return null;
    }

    public ResponseEntity<?> likePost(Account user, Long postId) {
        try {
            Post post = rootRepository.findById(postId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            List<Account> likedUsers = post.getLikedUsers();
            if (likedUsers.contains(user)) {
                likedUsers.remove(user);
            } else {
                likedUsers.add(user);
            }
            rootRepository.save(post);
            return ResponseEntity.ok().build();
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    public PaginatedResponseDTO<PostDetailsResponseDTO> getListPostsLiked(Account user, int page, int size) {
        Account userFound = accountRepository.findById(user.getId()).get();
        Pageable pageable = PageRequest.of(page, size);
        return convertToPostDetailDTO(userFound, rootRepository.findAllLikedPostsByUserId(user.getId(), user.getLanguage(), pageable));
    }

    public PaginatedResponseDTO<PostDetailsResponseDTO> getUsersPostedPosts(Account user, int page, int size) {
        Account userFound = accountRepository.findById(user.getId()).get();
        Pageable pageable = PageRequest.of(page, size);
        return convertToPostDetailDTO(userFound, rootRepository.getPostedPost(user.getId(), pageable));
    }

    public List<PostDetailsResponseDTO> getRecommendPostsBySession(Account user, EDaySession session) {
        Account userFound = accountRepository.findById(user.getId()).get();
        Pageable pageable = PageRequest.of(0, 10);
        return convertToPostDetailDTO(userFound, rootRepository.getPostsBySession(session, pageable)).getData();
    }

    public PaginatedResponseDTO<PostDetailsResponseDTO> getRecommendPostsByKitchen(Account user) {
        Account userFound = accountRepository.findById(user.getId()).get();
        Pageable pageable = PageRequest.of(0, 10);
        List<Long> toolIds = userFound.getTools().stream().map(KitchenTool::getId).toList();
        List<Long> spiceIds = userFound.getSpices().stream().map(KitchenSpice::getId).toList();
        List<KitchenIngredientRequestDTO> ingredientQuantities = userFound.getUserIngredients().stream().map(userIngredient -> {
            KitchenIngredient ingredient = userIngredient.getIngredient();
            return KitchenIngredientRequestDTO.builder()
                    .id(ingredient.getId())
                    .quantity(userIngredient.getQuantity())
                    .build();
        }).toList();
        return convertToPostDetailDTO(userFound, rootRepository.getPostsByKitchen(toolIds, spiceIds, ingredientQuantities, pageable));
    }
}
