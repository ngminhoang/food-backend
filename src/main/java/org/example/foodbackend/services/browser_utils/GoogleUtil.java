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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Base64;

@Slf4j
@Service
public class GoogleUtil {

    @Autowired
    IngredientRepository ingredientRepository;

    protected WebDriver driver;
    private static final String IMAGE_DIRECTORY = "db_data/images/igredient/";

    public GoogleUtil() {
        try{
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        catch (Exception ex){
            driver = null;
        }

    }

    public String saveBase64Image(String base64Image) throws IOException {
        if (base64Image == null || !base64Image.startsWith("data:image")) {
            System.err.println("Invalid Base64 input: " + base64Image); // Log the error
            return null; // Return null or an appropriate fallback value
        }

        try {
            String base64Data = base64Image.split(",")[1]; // Extract the data
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            String fileName = UUID.randomUUID().toString() + ".png";
            String filePath = IMAGE_DIRECTORY + fileName;

            File directory = new File(IMAGE_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                fos.write(imageBytes);
            }

            return "/images/igredient/" + fileName;
        } catch (Exception e) {
            System.err.println("Error decoding Base64 image: " + e.getMessage());
            return null; // Handle errors gracefully
        }
    }





    public void performDailySearch() {
//        WebDriver driver = null; // Local instance
        try {
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();

            Pageable pageable = PageRequest.of(0, 50);
            List<Ingredient> ingredientList = ingredientRepository.findAllWhereNoImg(pageable);

            for (Ingredient ingredient : ingredientList) {
                // Navigate to Google Image Search for the ingredient name
                driver.get("https://www.google.com/search?udm=2&q=" + ingredient.getName() + "&tbm=isch");

                // Wait for the images to load
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img")));

                // Find all <img> elements under the result container
                List<WebElement> imageElements = driver.findElements(By.xpath("//img"));

                // Retrieve only the first 3 image URLs
                List<String> imageUrls = new ArrayList<>();
                int count = 0;

                for (WebElement imgElement : imageElements) {
                    if (count >= 3) break;

                    String imgUrl = imgElement.getAttribute("src");
                    if (imgUrl == null || imgUrl.isEmpty()) {
                        imgUrl = imgElement.getAttribute("data-src"); // Fallback to data-src
                    }

                    if (imgUrl != null && !imgUrl.isEmpty()) {
                        try {
                            // Retrieve the image height
                            String heightStr = imgElement.getAttribute("height");
                            int height = 0; // Default height
                            if (heightStr != null && !heightStr.isEmpty()) {
                                height = Integer.parseInt(heightStr); // Parse height if available
                            }

                            // Proceed only if the height is less than 250
                            if (height > 170) {
                                if (imgUrl.startsWith("data:image")) {
                                    // Save Base64 image
                                    imageUrls.add(saveBase64Image(imgUrl));
                                } else if (imgUrl.startsWith("http")) {
                                    continue;
//                                    imageUrls.add(imgUrl); // Process HTTP image URL
                                } else {
                                    System.out.println("Unhandled image format: " + imgUrl); // Log unhandled cases
                                }
                                count++;
                            }
                        } catch (IllegalArgumentException e) {
                            System.err.println("Failed to process image: " + imgUrl + " - " + e.getMessage());
                        }
                    }
                }


                // Set the image URLs for the ingredient and save it to the database
                ingredient.setImgPaths(imageUrls);
                ingredientRepository.save(ingredient);

                // Log or perform further operations on the image URLs
                for (String url : imageUrls) {
                    System.out.println("Image URL: " + url);
                }
            }
            // Existing search logic...
        } catch (Exception e) {
            log.error("Error performing daily search", e);
        }
//        finally {
//            if (driver != null) {
//                driver.quit();
//            }
//        }
    }






    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
