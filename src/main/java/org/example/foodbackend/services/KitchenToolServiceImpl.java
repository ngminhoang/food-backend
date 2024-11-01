package org.example.foodbackend.services;

import org.example.foodbackend.entities.KitchenTool;
import org.example.foodbackend.repositories.KitchenToolRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class KitchenToolServiceImpl extends BaseServiceImpl<KitchenTool, Long, KitchenToolRepository> implements KitchenToolService {
    public KitchenToolServiceImpl(KitchenToolRepository rootRepository) {
        super(rootRepository);
    }
}
