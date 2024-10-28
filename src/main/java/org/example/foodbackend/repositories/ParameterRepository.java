package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Parameter;
import org.example.foodbackend.repositories.consts.NutritionalRanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query("SELECT p FROM Parameter p WHERE " +
            "p.sumCalories BETWEEN :sumCalories - " + NutritionalRanges.CALORIE_RANGE + " AND :sumCalories + " + NutritionalRanges.CALORIE_RANGE + " AND " +
            "p.sumProteins BETWEEN :sumProteins - " + NutritionalRanges.PROTEIN_RANGE + " AND :sumProteins + " + NutritionalRanges.PROTEIN_RANGE + " AND " +
            "p.sumCarbs BETWEEN :sumCarbs - " + NutritionalRanges.CARB_RANGE + " AND :sumCarbs + " + NutritionalRanges.CARB_RANGE + " AND " +
            "p.sumFibers BETWEEN :sumFibers - " + NutritionalRanges.FIBER_RANGE + " AND :sumFibers + " + NutritionalRanges.FIBER_RANGE + " AND " +
            "p.sumFats BETWEEN :sumFats - " + NutritionalRanges.FAT_RANGE + " AND :sumFats + " + NutritionalRanges.FAT_RANGE + " AND " +
            "p.sumSatFats BETWEEN :sumSatFats - " + NutritionalRanges.SAT_FAT_RANGE + " AND :sumSatFats + " + NutritionalRanges.SAT_FAT_RANGE)
    List<Parameter> findClosestMatchs(@Param("sumCalories") Double sumCalories,
                                      @Param("sumProteins") Double sumProteins,
                                      @Param("sumCarbs") Double sumCarbs,
                                      @Param("sumFibers") Double sumFibers,
                                      @Param("sumFats") Double sumFats,
                                      @Param("sumSatFats") Double sumSatFats);
}
