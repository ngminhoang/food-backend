package org.example.foodbackend.services;

import io.github.bonigarcia.wdm.WebDriverManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class SeleniumService {

    private final WebDriver driver;

    // Initialize WebDriver with Chromium in headless mode
    public SeleniumService() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }


    // Scheduled task to search for food and capture images
    @Scheduled(fixedDelay = 2) // This will run every 24 hours
    public void scheduledSearchAndCapture() {
        captureFoodImages("apple");
    }

    // Method to search for food and capture images
    public void captureFoodImages(String foodName) {
        try {
            // Navigate to Google Images search for the food
            driver.get("https://www.google.com/search?tbm=isch&q=" + foodName);
            Thread.sleep(2000); // Wait for the page to load

            // Find image elements in the results
            List<WebElement> imageElements = driver.findElements(By.cssSelector("img"));
            String screenshotDir = "./screenshots/" + foodName;
            Files.createDirectories(Paths.get(screenshotDir));

            // Capture and save screenshots for the first 3 images
            for (int i = 0; i < Math.min(3, imageElements.size()); i++) {
                WebElement image = imageElements.get(i);
                image.click(); // Click the image to enlarge it
                Thread.sleep(1000); // Wait for the image to load

                // Take a screenshot of the enlarged image
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

                // Save the screenshot
                Path destinationPath = Paths.get(screenshotDir, foodName + "_result_" + (i + 1) + ".png");
                Files.copy(screenshot.toPath(), destinationPath);
            }

            System.out.println("Images captured and saved for: " + foodName);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

