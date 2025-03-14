package org.example.foodbackend.services;

import org.example.foodbackend.entities.DaySession;
import org.example.foodbackend.repositories.DaySessionRepository;
import org.example.foodbackend.services.base.BaseService;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DaySessionService extends BaseServiceImpl<DaySession, Long, DaySessionRepository> implements BaseService<DaySession, Long> {
    public DaySessionService(DaySessionRepository rootRepository) {
        super(rootRepository);
    }
}
