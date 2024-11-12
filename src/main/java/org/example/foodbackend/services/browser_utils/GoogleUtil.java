package org.example.foodbackend.services.browser_utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.repositories.IngredientRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class GoogleUtil {

    @Autowired
    IngredientRepository ingredientRepository;

    protected WebDriver driver;

    public GoogleUtil() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public void performDailySearch() {
        try {
            Pageable pageable = PageRequest.of(0, 50);
            List<Ingredient> ingredientList = ingredientRepository.findAllWhereNoImg(pageable);

            for (Ingredient ingredient : ingredientList) {
                // Điều hướng đến Google Image Search cho tên của ingredient
                driver.get("https://www.google.com/search?udm=2&q=" + ingredient.getName() + "&tbm=isch");

                // Đợi cho các hình ảnh được load
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("rg_i")));

                // Lấy danh sách các thẻ img có class "rg_i Q4LuWd" và chỉ lấy đúng 3 URL
                List<String> imageUrls = new ArrayList<>();
                List<WebElement> imageElements = driver.findElements(By.cssSelector("img.rg_i.Q4LuWd"));

                int count = 0;
                for (WebElement imgElement : imageElements) {
                    if (count >= 3) break;

                    String imgUrl = imgElement.getAttribute("src");
                    imageUrls.add(imgUrl);
                    count++;
//                    if (imgUrl == null || imgUrl.isEmpty()) {
//                        imgUrl = imgElement.getAttribute("data-src");
//                    }
//
//                    if (imgUrl != null && !imgUrl.isEmpty()) {
//                        imageUrls.add(imgUrl);
//                        count++;
//                    }
                }

                // In ra các URL hoặc lưu vào database theo nhu cầu của bạn
                for (String url : imageUrls) {
                    System.out.println("Image URL: " + url);
                    // Lưu URL vào cơ sở dữ liệu nếu cần thiết, ví dụ:
                    // ingradientRepository.updateImageUrl(ingradient.getId(), url);
                }
            }

        } catch (Exception e) {
            log.error("Error performing daily search", e);
        } finally {
            // Đóng driver hoặc các resource nếu cần
        }
    }



    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
