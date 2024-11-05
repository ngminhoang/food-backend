package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenSpice;
import org.example.foodbackend.repositories.AccountRepository;
import org.example.foodbackend.repositories.KitchenSpiceRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class KitchenSpiceServiceImpl extends BaseServiceImpl<KitchenSpice, Long, KitchenSpiceRepository> implements KitchenSpiceService {
    private final AccountRepository accountRepository;

    @Autowired
    public KitchenSpiceServiceImpl(KitchenSpiceRepository rootRepository, KitchenSpiceRepository kitchenSpiceRepository, AccountRepository accountRepository) {
        super(rootRepository);
        this.accountRepository = accountRepository;
    }

    @Override
    public ResponseEntity<List<KitchenSpice>> getListSpicesNotAdded(Account user) {
        Account account = accountRepository.findById(user.getId()).get();
        return ResponseEntity.ok(rootRepository.getListSpicesNotAdded(account));
    }

    @Override
    public ResponseEntity<List<KitchenSpice>> addUserSpices(Account user, List<Long> spiceIds) {
        List<KitchenSpice> kitchenSpices = rootRepository.findAllById(spiceIds);
        Account account = accountRepository.findById(user.getId()).get();
        account.setSpices(kitchenSpices);
        accountRepository.save(account);
        return ResponseEntity.ok(kitchenSpices);
    }

    @Override
    public ResponseEntity<List<KitchenSpice>> getUserSpices(Account user) {
        Account account = accountRepository.findById(user.getId()).get();
        return ResponseEntity.ok(account.getSpices());
    }
}
