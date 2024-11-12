package org.example.foodbackend.services.browser_utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchService {

    @Autowired
    GoogleUtil googleUtil;


    public void performDailySearch() {
        // Use ExecutorService with 2 threads
//
        googleUtil.performDailySearch();
//        yahooUtil.performDailySearch();

    }

}


