package org.example.foodbackend.services;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.repositories.ParameterRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ParameterServiceImpl  extends BaseServiceImpl<Parameter, Long, ParameterRepository> implements ParameterService {
    public ParameterServiceImpl(ParameterRepository repository) {
        super(repository);
    }
}
