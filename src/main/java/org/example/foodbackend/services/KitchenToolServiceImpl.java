package org.example.foodbackend.services;

import jakarta.transaction.Transactional;
import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.repositories.AccountRepository;
import org.example.foodbackend.repositories.KitchenToolRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class KitchenToolServiceImpl extends BaseServiceImpl<KitchenTool, Long, KitchenToolRepository> implements KitchenToolService {
    private final AccountRepository accountRepository;

    @Autowired
    public KitchenToolServiceImpl(KitchenToolRepository rootRepository, AccountRepository accountRepository, KitchenToolRepository kitchenToolRepository) {
        super(rootRepository);
        this.accountRepository = accountRepository;
    }


    @Override
    public ResponseEntity<List<KitchenTool>> getAllKitchenTools(Account user) {
        Account account = accountRepository.findById(user.getId()).get();
        return ResponseEntity.ok(rootRepository.getKitchenToolsNotAdded(account));
    }

    @Override
    public ResponseEntity<List<KitchenTool>> getUserKitchenTools(Account user) {
        Account account = accountRepository.findById(user.getId()).get();
        return ResponseEntity.ok(account.getTools());
    }

    @Override
    @Transactional
    public ResponseEntity<List<KitchenTool>> addUserKitchenTool(Account user, List<Long> kitchenToolIds) {
        try {
            Account account = accountRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
            List<KitchenTool> kitchenTools = rootRepository.findAllById(kitchenToolIds);
            List<KitchenTool> oldKitchenTools = account.getTools();
            kitchenTools.addAll(oldKitchenTools);
            account.setTools(kitchenTools);
            accountRepository.save(account);
            return ResponseEntity.ok(kitchenTools);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<KitchenTool> deleteUserKitchenTool(Account user, Long kitchenToolId) {
        try {
            KitchenTool kitchenTool = rootRepository.findById(kitchenToolId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            Account account = accountRepository.findById(user.getId()).get();
            account.getTools().remove(kitchenTool);
            accountRepository.save(account);
            return ResponseEntity.ok(kitchenTool);
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
