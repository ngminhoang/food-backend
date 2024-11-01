package org.example.foodbackend.services;

import org.example.foodbackend.entities.KitchenSpice;
import org.example.foodbackend.repositories.KitchenSpiceRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class KitchenSpiceServiceImpl extends BaseServiceImpl<KitchenSpice, Long, KitchenSpiceRepository> implements KitchenSpiceService {
    public KitchenSpiceServiceImpl(KitchenSpiceRepository rootRepository) {
        super(rootRepository);
    }
}
