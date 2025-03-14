package org.example.foodbackend.services;

import org.example.foodbackend.entities.Account;
import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface KitchenToolService extends BaseService<KitchenTool, Long> {
    ResponseEntity<List<KitchenTool>> getAllKitchenTools(Account user);
    ResponseEntity<List<KitchenTool>> getUserKitchenTools(Account user);
    ResponseEntity<List<KitchenTool>> addUserKitchenTool(Account user, List<Long> kitchenToolIds);
    ResponseEntity<KitchenTool> deleteUserKitchenTool(Account user, Long kitchenToolId);

}
