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
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    public String saveBase64Image(String base64Image) throws IOException {
        // Ensure the directory exists
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            // Create the directory if it does not exist
            directory.mkdirs();
        }

        // Check if the Base64 string contains a MIME type prefix (e.g., "data:image/png;base64,")
        String base64Data = base64Image.contains(",") ? base64Image.split(",")[1] : base64Image;

        // Generate a unique file name with .png extension
        String fileName = UUID.randomUUID().toString() + ".png";
        String filePath = IMAGE_DIRECTORY + fileName;

        // Decode the Base64 image string
        byte[] imageBytes = Base64.getDecoder().decode(base64Data);

        // Save the image to the specified path
        File imageFile = new File(filePath);
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(imageBytes);
        }

        // Return the relative path of the saved image
        return "/images/igredient/" + fileName;
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
                    imageUrls.add(saveBase64Image(imgUrl));
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

                ingredient.setImgPaths(imageUrls);
                Ingredient x = ingredientRepository.save(ingredient);
                // In ra các URL hoặc lưu vào database theo nhu cầu của bạn
                for (String url : x.getImgPaths()) {
                    System.out.println("Image URL: " + url);
//                    String img = saveBase64Image(url);
                    // Lưu URL vào cơ sở dữ liệu nếu cần thiết, ví dụ:
//                     ingredientRepository.updateImageUrl(ingradient.getId(), url);
                }
            }
//            ingredientRepository.saveAll(ingredientList);

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
