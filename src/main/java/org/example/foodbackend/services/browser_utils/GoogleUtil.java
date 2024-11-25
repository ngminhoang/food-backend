package org.example.foodbackend.services.browser_utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.repositories.IngredientRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.*;

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

    public List<String> fetchProxies() {
        String proxyUrl = "https://api.proxyscrape.com/v4/free-proxy-list/get?request=display_proxies&country=vn&protocol=http&proxy_format=protocolipport&format=text&timeout=10000";
        List<String> proxies = new ArrayList<>();

        try {
            URL url = new URL(proxyUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    proxies.add(line.trim());
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching proxies: " + e.getMessage());
        }
        return proxies;
    }

    public WebDriver createDriverWithProxy(String proxyAddress) {
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress).setSslProxy(proxyAddress);

        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);

        return new ChromeDriver(options);
    }

    public WebDriver getDriverWithRandomProxy() {
        List<String> proxyList = fetchProxies();
        Random random = new Random();
        String proxyAddress = proxyList.get(random.nextInt(proxyList.size()));
        log.info("Using proxy: " + proxyAddress);
        return createDriverWithProxy(proxyAddress);
    }

    public WebDriver getDriverWithDynamicProxy() {
        List<String> proxies = fetchProxies();
        if (proxies.isEmpty()) {
            System.err.println("No proxies available. Using default connection.");
            return new ChromeDriver(); // Fallback to no proxy
        }
        Random random = new Random();
        String proxyAddress = proxies.get(random.nextInt(proxies.size()));
        log.info("Using proxy: " + proxyAddress);
        return createDriverWithProxy(proxyAddress);
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
        try {
            Pageable pageable = PageRequest.of(0, 50);
            List<Ingredient> ingredientList = ingredientRepository.findAllWhereNoImg(pageable);
            log.info("count of ingredients has no image: " + String.valueOf(ingredientList.size()));
            long sessionStartTime = System.currentTimeMillis(); // Record the session start time

            for (Ingredient ingredient : ingredientList) {
                // Check if the session duration exceeds 1 minute (60,000 ms)
                if (System.currentTimeMillis() - sessionStartTime > 60000) {
                    log.info("Restarting browser session to avoid detection.");
                    driver.quit();
                    driver = new ChromeDriver(); // Restart the browser
                    sessionStartTime = System.currentTimeMillis(); // Reset session start time
                }

                try {
                    // Navigate to Google Image Search for the ingredient name
                    driver.get("https://www.google.com/search?udm=2&q=" + ingredient.getName() + "&tbm=isch");

                    // Wait for the images to load
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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

                                // Proceed only if the height is greater than 170
                                if (height > 170) {
                                    if (imgUrl.startsWith("data:image")) {
                                        // Save Base64 image
                                        imageUrls.add(saveBase64Image(imgUrl));
                                    } else if (imgUrl.startsWith("http")) {
                                        // Process HTTP image URL
                                        continue;
//                                        imageUrls.add(imgUrl);
                                    } else {
                                        log.warn("Unhandled image format: " + imgUrl); // Log unhandled cases
                                    }
                                    count++;
                                }
                            } catch (IllegalArgumentException e) {
                                log.error("Failed to process image: " + imgUrl, e);
                            }
                        }
                    }

                    // Set the image URLs for the ingredient and save it to the database
                    ingredient.setImgPaths(imageUrls);
                    ingredientRepository.save(ingredient);

                    // Log or perform further operations on the image URLs
                    for (String url : imageUrls) {
                        log.info("Image URL: " + url);
                    }
                } catch (Exception e) {
                    log.error("Error processing ingredient: {}", ingredient.getName(), e);
                    driver.quit(); // Quit the browser in case of an error
                    driver = getDriverWithDynamicProxy(); // Restart the browser
                    sessionStartTime = System.currentTimeMillis(); // Reset session start time
                }
            }
        } catch (Exception e) {
            log.error("Error performing daily search", e);
        }
//        finally {
//            if (driver != null) {
//                driver.quit();
//            }
//        }
    }

//
//    public void performDailySearch() {
////        WebDriver driver = null; // Local instance
//        try {
////            WebDriverManager.chromedriver().setup();
////            driver = getDriverWithDynamicProxy();
//
//
//            Pageable pageable = PageRequest.of(0, 50);
//            List<Ingredient> ingredientList = ingredientRepository.findAllWhereNoImg(pageable);
//
//            for (Ingredient ingredient : ingredientList) {
//                // Navigate to Google Image Search for the ingredient name
//                driver.get("https://www.google.com/search?udm=2&q=" + ingredient.getName() + "&tbm=isch");
//
//                // Wait for the images to load
//                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
//                wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img")));
//
//                // Find all <img> elements under the result container
//                List<WebElement> imageElements = driver.findElements(By.xpath("//img"));
//
//                // Retrieve only the first 3 image URLs
//                List<String> imageUrls = new ArrayList<>();
//                int count = 0;
//
//                for (WebElement imgElement : imageElements) {
//                    if (count >= 3) break;
//
//                    String imgUrl = imgElement.getAttribute("src");
//                    if (imgUrl == null || imgUrl.isEmpty()) {
//                        imgUrl = imgElement.getAttribute("data-src"); // Fallback to data-src
//                    }
//
//                    if (imgUrl != null && !imgUrl.isEmpty()) {
//                        try {
//                            // Retrieve the image height
//                            String heightStr = imgElement.getAttribute("height");
//                            int height = 0; // Default height
//                            if (heightStr != null && !heightStr.isEmpty()) {
//                                height = Integer.parseInt(heightStr); // Parse height if available
//                            }
//
//                            // Proceed only if the height is less than 250
//                            if (height > 170) {
//                                if (imgUrl.startsWith("data:image")) {
//                                    // Save Base64 image
//                                    imageUrls.add(saveBase64Image(imgUrl));
//                                } else if (imgUrl.startsWith("http")) {
//                                    continue;
////                                    imageUrls.add(imgUrl); // Process HTTP image URL
//                                } else {
//                                    System.out.println("Unhandled image format: " + imgUrl); // Log unhandled cases
//                                }
//                                count++;
//                            }
//                        } catch (IllegalArgumentException e) {
//                            System.err.println("Failed to process image: " + imgUrl + " - " + e.getMessage());
//                        }
//                    }
//                }
//
//
//                // Set the image URLs for the ingredient and save it to the database
//                ingredient.setImgPaths(imageUrls);
//                ingredientRepository.save(ingredient);
//
//                // Log or perform further operations on the image URLs
//                for (String url : imageUrls) {
//                    System.out.println("Image URL: " + url);
//                }
//            }
//            // Existing search logic...
//        } catch (Exception e) {
//            log.error("Error performing daily search", e);
//        }
//        finally {
//            if (driver != null) {
//                driver.quit();
//            }
//        }
//    }






    public void shutdown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
