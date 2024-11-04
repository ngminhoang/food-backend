package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.repositories.AccountRepository;
import org.example.foodbackend.repositories.KitchenToolRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KitchenToolServiceImpl extends BaseServiceImpl<KitchenTool, Long, KitchenToolRepository> implements KitchenToolService {
    private final AccountRepository accountRepository;
    private final KitchenToolRepository kitchenToolRepository;

    @Autowired
    public KitchenToolServiceImpl(KitchenToolRepository rootRepository, AccountRepository accountRepository, KitchenToolRepository kitchenToolRepository) {
        super(rootRepository);
        this.accountRepository = accountRepository;
        this.kitchenToolRepository = kitchenToolRepository;
    }

    @Override
    public ResponseEntity<Set<KitchenTool>> getUserKitchenTools(Account user) {
        Account account = accountRepository.findById(user.getId()).get();
        Hibernate.initialize(account.getListTools());
        return ResponseEntity.ok(account.getListTools());
    }

    @Override
    public ResponseEntity<List<KitchenTool>> addUserKitchenTool(Account user, List<Long> kitchenToolIds) {
        try {
            Account account = accountRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
            List<KitchenTool> kitchenTools = kitchenToolRepository.findAllById(kitchenToolIds);
//            for (KitchenTool kitchenTool : kitchenTools) {
//                kitchenTool.set
//            }
            account.setListTools(new HashSet<>(kitchenTools));
            System.out.println(account.getListTools());
            accountRepository.save(account);
            return ResponseEntity.ok(kitchenTools);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public KitchenTool deleteUserKitchenTool(Account user, Long kitchenToolId) {
        return null;
    }
}
