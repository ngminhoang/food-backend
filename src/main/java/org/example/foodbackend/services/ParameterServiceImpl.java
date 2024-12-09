package org.example.foodbackend.services;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.entities.dto.ParameterResponseDTO;
import org.example.foodbackend.other_services.PythonAPI;
import org.example.foodbackend.repositories.MeilisearchRepository;
import org.example.foodbackend.repositories.ParameterRepository;
import org.example.foodbackend.services.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ParameterServiceImpl extends BaseServiceImpl<Parameter, Long, ParameterRepository> implements ParameterService {
    public ParameterServiceImpl(ParameterRepository repository) {
        super(repository);
    }

    @Autowired
    PythonAPI pythonAPI;

    @Override
    public ResponseEntity<ParameterResponseDTO> findByNutrientProperties(Parameter parameter) {

        List<Parameter> results = rootRepository.findClosestMatchs(parameter.getSumCalories(), parameter.getSumProteins(), parameter.getSumCarbs(), parameter.getSumFibers(), parameter.getSumFats(), parameter.getSumSatFats());
        Parameter result = new Parameter();
        try{
            result = results.get(0);
        }
        catch (Exception ex){
            result = null;
        }
        if (result != null) {
            return ResponseEntity.ok(new ParameterResponseDTO(result));
        }
        result = pythonAPI.calculate(parameter.getSumCalories(), parameter.getSumProteins(), parameter.getSumCarbs(), parameter.getSumFibers(), parameter.getSumFats(), parameter.getSumSatFats());
        return ResponseEntity.ok(new ParameterResponseDTO(result));
    }

    @Override
    public Integer getCount() {
        return Math.toIntExact(rootRepository.count());
    }

    @Override
    public ResponseEntity<ParameterResponseDTO> findByBodyProperties(Double weight, Double height, Integer age, String gender, String activityLevel) {

        try {
            // Ensure inputs are not null and process parameters
            if (weight == null || height == null || age == null || gender == null || activityLevel == null) {
                return ResponseEntity.badRequest().build();
            }

            // Convert gender and activity level to lowercase for easier comparison
            gender = gender.toLowerCase();
            activityLevel = activityLevel.toLowerCase();

            // BMR calculation based on gender using Mifflin-St Jeor formula
            double BMR;
            if ("male".equals(gender)) {
                BMR = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
            } else if ("female".equals(gender)) {
                BMR = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
            } else {
                return ResponseEntity.badRequest().build();
            }

            // Activity multipliers for TDEE calculation
            Map<String, Double> activityMultipliers = Map.of(
                    "sedentary", 1.2,
                    "lightly_active", 1.375,
                    "moderately_active", 1.55,
                    "very_active", 1.725,
                    "super_active", 1.9
            );

            Double multiplier = activityMultipliers.get(activityLevel);
            if (multiplier == null) {
                return ResponseEntity.badRequest().build();
            }

            // TDEE calculation
            double TDEE = BMR * multiplier;

            // Macronutrient ratios
            double proteinRatio = 0.25;
            double carbRatio = 0.50;
            double fatRatio = 0.25;

            // Macronutrient calculation
            double protein = (proteinRatio * TDEE) / 4;   // grams of protein
            double carbs = (carbRatio * TDEE) / 4;        // grams of carbohydrates
            double fats = (fatRatio * TDEE) / 9;          // grams of fat

            // Saturated fats calculation (10% of total calories)
            double satFat = (0.1 * TDEE) / 9;

            // Recommended fiber intake
            double fiber = "male".equals(gender) ? 38 : 25;

            // BMI calculation
            double BMI = weight / Math.pow(height / 100, 2);


            Parameter parameter = Parameter.builder()
                    .sumFats(fats)
                    .sumSatFats(satFat)
                    .sumCalories(TDEE)
                    .sumProteins(protein)
                    .sumCarbs(carbs)
                    .sumFibers(fiber)
                    .build();

            List<Parameter> results = rootRepository.findClosestMatchs(parameter.getSumCalories(), parameter.getSumProteins(), parameter.getSumCarbs(), parameter.getSumFibers(), parameter.getSumFats(), parameter.getSumSatFats());
            Parameter result = new Parameter();
            try{
                result = results.get(0);
            }
            catch (Exception ex){
                result = null;
            }
            if (result != null) {
                return ResponseEntity.ok(new ParameterResponseDTO(result));
            }
            result = pythonAPI.calculate(parameter.getSumCalories(), parameter.getSumProteins(), parameter.getSumCarbs(), parameter.getSumFibers(), parameter.getSumFats(), parameter.getSumSatFats());
            return ResponseEntity.ok(new ParameterResponseDTO(result));

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    private Parameter findClosestMatch(Parameter parameter, List<Parameter> parameters) {
        if (parameters == null || parameters.isEmpty()) {
            return null; // Nếu không có đối tượng nào, trả về null
        }

        Parameter closestMatch = null;
        double minDeviation = Double.MAX_VALUE; // Khởi tạo giá trị biến thiên nhỏ nhất với giá trị lớn nhất

        for (Parameter p : parameters) {
            // Tính toán tổng biến thiên
            double deviation = Math.abs(p.getSumCalories() - parameter.getSumCalories())
                    + Math.abs(p.getSumProteins() - parameter.getSumProteins())
                    + Math.abs(p.getSumCarbs() - parameter.getSumCarbs())
                    + Math.abs(p.getSumFibers() - parameter.getSumFibers())
                    + Math.abs(p.getSumFats() - parameter.getSumFats())
                    + Math.abs(p.getSumSatFats() - parameter.getSumSatFats());

            // Kiểm tra xem biến thiên có nhỏ hơn biến thiên nhỏ nhất không
            if (deviation < minDeviation) {
                minDeviation = deviation; // Cập nhật giá trị biến thiên nhỏ nhất
                closestMatch = p; // Cập nhật đối tượng có biến thiên nhỏ nhất
            }
        }
        return closestMatch; // Trả về đối tượng có biến thiên nhỏ nhất
    }


}
