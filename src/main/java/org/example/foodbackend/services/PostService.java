package org.example.foodbackend.services;

import org.aspectj.apache.bcel.generic.Instruction;
import org.example.foodbackend.entities.*;
import org.example.foodbackend.entities.dto.PostRequestDTO;
import org.example.foodbackend.repositories.*;
import org.example.foodbackend.services.base.BaseService;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public PostService(PostRepository rootRepository, AccountRepository accountRepository, InstructionStepRepository instructionStepRepository, KitchenToolRepository kitchenToolRepository, KitchenSpiceRepository kitchenSpiceRepository, KitchenIngredientRepository kitchenIngredientRepository, PostIngredientRepository postIngredientRepository) {
        super(rootRepository);
        this.accountRepository = accountRepository;
        this.instructionStepRepository = instructionStepRepository;
        this.kitchenToolRepository = kitchenToolRepository;
        this.kitchenSpiceRepository = kitchenSpiceRepository;
        this.kitchenIngredientRepository = kitchenIngredientRepository;
        this.postIngredientRepository = postIngredientRepository;
    }

    public ResponseEntity<?> addPost(Account user, PostRequestDTO postRequestDTO) {
        Account account = accountRepository.findById(user.getId()).get();
        List<KitchenTool> listTools = kitchenToolRepository.findAllById(postRequestDTO.getTools());
        List<KitchenSpice> listSpices = kitchenSpiceRepository.findAllById(postRequestDTO.getSpices());

        Post post = Post.builder()
                .dish_name(postRequestDTO.getDish_name())
                .dish_img_url(postRequestDTO.getDish_img_url())
                .description(postRequestDTO.getDescription())
                .language(postRequestDTO.getLanguage())
                .daySessions(postRequestDTO.getSessions())
                .user(account)
                .tools(listTools)
                .spices(listSpices)
                .is_standard(false)
                .build();
        Post postReturn = rootRepository.save(post);
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
        };

        return ResponseEntity.ok(post);
    }
}
