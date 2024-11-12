package org.example.foodbackend.services.browser_utils;

import org.example.foodbackend.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ScheduleService {

    @Autowired
    private SearchService searchService;

    @Autowired
    IngredientRepository ingredientRepository;

    // Runs daily at midnight using cron expression
//    @Scheduled(fixedDelay = 1)
    public void scheduleDailySearch() {


        searchService.performDailySearch();


        // Gọi phương thức thực hiện tìm kiếm hàng ngày
    }
}