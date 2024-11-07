package org.example.foodbackend.services.browser_utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.example.foodbackend.entities.Ingradient;
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
//        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public void performDailySearch() {
        try {
            Pageable pageable = PageRequest.of(0, 50);
            List<Ingradient> ingradientList = ingredientRepository.findAllWhereNoImg(pageable);

            for (Ingradient ingradient : ingradientList) {
                // Navigate to Google search for the food
                driver.get("https://www.google.com/search?udm=2&q=" + ingradient.getName() + "&tbm=isch");

                // Wait for the page to load
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("img")));

                // Locate the image elements on the page
                List<WebElement> imageElements = driver.findElements(By.cssSelector("img"));

                // Limit to first 3 images (or fewer if there are less than 3)
                List<String> imageUrls = new ArrayList<>();
                for (int i = 0; i < Math.min(3, imageElements.size()); i++) {
                    WebElement image = imageElements.get(i);
                    String imageUrl = image.getAttribute("src"); // Get the URL of the image
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        imageUrls.add(imageUrl);
                    }
                }

                // Check if we found image URLs
                if (!imageUrls.isEmpty()) {
                    // Save the image URLs to the Ingradient entity
                    ingradient.setImgPaths(imageUrls);  // Assuming imgPaths is a List<String> in Ingradient entity
                    ingredientRepository.save(ingradient); // Save to the database
                    log.info("Image URLs saved for: " + ingradient.getName());
                } else {
                    log.warn("No images found for: " + ingradient.getName());
                }
            }

        } catch (Exception e) {
            log.error("Error performing daily search", e);
        } finally {

        }
    }

    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
