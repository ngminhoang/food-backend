package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenSpice;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KitchenSpiceService extends BaseService<KitchenSpice, Long> {
    ResponseEntity<List<KitchenSpice>> addUserSpices(Account user, List<Long> spiceIds);
}
