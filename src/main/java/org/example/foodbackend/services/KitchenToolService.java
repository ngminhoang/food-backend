package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface KitchenToolService extends BaseService<KitchenTool, Long> {
    ResponseEntity<Set<KitchenTool>> getUserKitchenTools(Account user);
    ResponseEntity<List<KitchenTool>> addUserKitchenTool(Account user, List<Long> kitchenToolIds);
    KitchenTool deleteUserKitchenTool(Account user, Long kitchenToolId);
}
