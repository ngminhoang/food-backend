package org.example.foodbackend.services;

import org.aspectj.apache.bcel.generic.Instruction;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.InstructionStep;
import org.example.foodbackend.entities.Post;
import org.example.foodbackend.entities.dto.PostRequestDTO;
import org.example.foodbackend.repositories.AccountRepository;
import org.example.foodbackend.repositories.InstructionStepRepository;
import org.example.foodbackend.repositories.PostRepository;
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

    public PostService(PostRepository rootRepository, AccountRepository accountRepository, InstructionStepRepository instructionStepRepository) {
        super(rootRepository);
        this.accountRepository = accountRepository;
        this.instructionStepRepository = instructionStepRepository;
    }

    public ResponseEntity<?> addPost(Account user, PostRequestDTO postRequestDTO) {
        Account account = accountRepository.findById(user.getId()).get();
        Post post = Post.builder()
                .dish_name(postRequestDTO.getDish_name())
                .dish_img_url(postRequestDTO.getDish_img_url())
                .description(postRequestDTO.getDescription())
                .language(postRequestDTO.getLanguage())
                .daySessions(postRequestDTO.getSessions())
                .user(account)
                .tools(postRequestDTO.getTools())
                .spices(postRequestDTO.getSpices())
                .ingredients(postRequestDTO.getIngredients())
                .is_standard(false)
                .build();
        Post postReturn = rootRepository.save(post);
        List<InstructionStep> stepList = postRequestDTO.getSteps().stream().map(step -> {
                InstructionStep instructionStep = InstructionStep.builder()
                            .step(step.getStep())
                            .title(step.getTitle())
                            .description(step.getDescription())
                            .img_url(step.getImg_url())
                            .duration(step.getDuration())
                            .post(postReturn)
                            .build();
                    return instructionStepRepository.save(instructionStep);
            }).toList();
//        for (InstructionStep step : postRequestDTO.getSteps()) {
//
//        }
        return ResponseEntity.ok(post);
    }
}
