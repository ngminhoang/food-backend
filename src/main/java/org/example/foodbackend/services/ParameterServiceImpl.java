package org.example.foodbackend.services;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterRequestDTO;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.example.foodbackend.other_services.PythonAPI;
import org.example.foodbackend.repositories.ParameterRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ParameterServiceImpl  extends BaseServiceImpl<Parameter, Long, ParameterRepository> implements ParameterService {
    public ParameterServiceImpl(ParameterRepository repository) {
        super(repository);
    }

    @Autowired
    PythonAPI pythonAPI;

    @Override
    public ResponseEntity<ParameterResponseDTO> findByProperties(Parameter parameter) {

//        Parameter result =  repository.findClosestMatch(parameter.getSumCalories(), parameter.getSumProteins(), parameter.getSumCarbs(), parameter.getSumFibers(), parameter.getSumFats(), parameter.getSumSatFats());
//        if(result != null) {
//            return ResponseEntity.ok(new ParameterResponseDTO(result));
//        }
        Parameter result = pythonAPI.calculate(parameter.getSumCalories(), parameter.getSumProteins(), parameter.getSumCarbs(), parameter.getSumFibers(), parameter.getSumFats(), parameter.getSumSatFats());
        return ResponseEntity.ok(new ParameterResponseDTO(result));
    }
}
