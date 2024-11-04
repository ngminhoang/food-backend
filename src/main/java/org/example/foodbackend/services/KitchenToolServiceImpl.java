package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.repositories.AccountRepository;
import org.example.foodbackend.repositories.KitchenToolRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class KitchenToolServiceImpl extends BaseServiceImpl<KitchenTool, Long, KitchenToolRepository> implements KitchenToolService {
    @Autowired
    private AccountRepository accountRepository;

    public KitchenToolServiceImpl(KitchenToolRepository rootRepository) {
        super(rootRepository);
    }

    @Override
    public ResponseEntity<Set<KitchenTool>> getUserKitchenTools(Account user) {
        try {
            Account account = accountRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
            System.out.println(account.getListTools());
            return ResponseEntity.ok(account.getListTools());
        } catch (ChangeSetPersister.NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<KitchenTool>> addUserKitchenTool(Account user, List<Long> kitchenToolIds) {
        try {
            Account account = accountRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);
            List<KitchenTool> kitchenTools = rootRepository.findAllById(kitchenToolIds);
            Set<KitchenTool> userListTools = account.getListTools();
            for (KitchenTool kitchenTool : kitchenTools) {
                System.out.println(kitchenTool.getId());
                userListTools.add(kitchenTool);
                kitchenTool.getListUsers().add(account);
            }
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
