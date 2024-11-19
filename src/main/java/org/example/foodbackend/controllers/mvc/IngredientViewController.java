package org.example.foodbackend.controllers.mvc;

import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class IngredientViewController {

    @Autowired
    private IngredientService ingredientService;

    // Hiển thị trang chính của quản lý ingredient
    @GetMapping("/ingredient-management")
    public String manageIngredients(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "isVerified", required = false) Boolean isVerified,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        // Lọc và tìm kiếm
        Page<Ingredient> ingredients = ingredientService.findIngredients(search, isVerified, page, size);

        model.addAttribute("ingredients", ingredients.getContent());
        model.addAttribute("totalPages", ingredients.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        model.addAttribute("isVerified", isVerified);
        return "ingredient-management";
    }

    // Hiển thị form thêm/sửa ingredient
    @GetMapping("/ingredient-form")
    public String showIngredientForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        Ingredient ingredient = id != null ? ingredientService.findById(id) : new Ingredient();
        model.addAttribute("ingredient", ingredient);
        return "ingredient-form";
    }

    // Lưu ingredient
    @PostMapping("/save")
    public String saveIngredient(@ModelAttribute("ingredient") Ingredient ingredient) {
        ingredientService.save(ingredient);
        return "redirect:/ingredient-management";
    }

    // Xóa ingredient
    @PostMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable("id") Long id) {
        ingredientService.deleteById(id);
        return "redirect:/ingredient-management";
    }


    @PostMapping("/upload-image")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadImage(
            @RequestParam("image") MultipartFile imageFile,
            @RequestParam(value = "id", required = false) Long ingredientId) {

        Map<String, Object> response = new HashMap<>();

        try {
            // Define the folder path where the image should be saved
            String folderPath = "db_data/images/ingredient/";
            Path directoryPath = Paths.get(folderPath);

            // Ensure the directory exists
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            // Generate a unique name for the image
            String imageName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path filePath = directoryPath.resolve(imageName);

            // Save the image to the directory
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Associate the image with the ingredient in the database
            if (ingredientId != null) {
                Ingredient ingredient = ingredientService.findById(ingredientId);
                List<String> imgPaths = ingredient.getImgPaths();
                imgPaths.add("/images/ingredient/" + imageName);
                ingredient.setImgPaths(imgPaths);
                ingredientService.save(ingredient);
            }

            // Prepare success response with redirection URL
            response.put("success", true);
            response.put("redirectUrl", "/ingredient-form?id=" + ingredientId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "Failed to upload the image.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/image-delete")
    public String deleteImage(@RequestParam("imgPath") String imgPath, @RequestParam("ingredientId") Long ingredientId) {
        try {
            // Find the ingredient by ID
            Ingredient ingredient = ingredientService.findById(ingredientId);

            // Get the list of image paths
            List<String> imgPaths = ingredient.getImgPaths();

            // Check if the imagePath exists in the list
            if (imgPaths.contains(imgPath)) {
                // Remove the image path from the list
                imgPaths.remove(imgPath);
                ingredient.setImgPaths(imgPaths);
                ingredientService.save(ingredient);

                // Delete the image file from the server (optional)
                Path filePath = Paths.get("db_data", imgPath.replace("/images/ingredient/", "images/ingredient/"));
                Files.deleteIfExists(filePath);
            }

            // Redirect to the ingredient form page after deletion
            return "redirect:/ingredient-form?id=" + ingredientId;

        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/ingredient-form?id=" + ingredientId + "&error=true"; // Redirect with error flag
        }
    }




}
