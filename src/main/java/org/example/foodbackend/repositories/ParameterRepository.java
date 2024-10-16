package org.example.foodbackend.repositories;

import org.example.foodbackend.entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    @Query("SELECT p FROM Parameter p ORDER BY " +
            "(ABS(p.sumCalories - :sumCalories) + " +
            "ABS(p.sumProteins - :sumProteins) + " +
            "ABS(p.sumCarbs - :sumCarbs) + " +
            "ABS(p.sumFibers - :sumFibers) + " +
            "ABS(p.sumFats - :sumFats) + " +
            "ABS(p.sumSatFats - :sumSatFats)) ASC")
    Parameter findClosestMatch(@Param("sumCalories") Double sumCalories,
                               @Param("sumProteins") Double sumProteins,
                               @Param("sumCarbs") Double sumCarbs,
                               @Param("sumFibers") Double sumFibers,
                               @Param("sumFats") Double sumFats,
                               @Param("sumSatFats") Double sumSatFats);
}
