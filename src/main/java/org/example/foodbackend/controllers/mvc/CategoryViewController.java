package org.example.foodbackend.controllers.mvc;

import org.example.foodbackend.entities.Category;
import org.example.foodbackend.entities.Category;
import org.example.foodbackend.entities.enums.FoodType;
import org.example.foodbackend.services.CategoryService;
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
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class CategoryViewController {

    @Autowired
    private CategoryService categoryService;

    // Hiển thị trang chính của quản lý category
    @GetMapping("/category-management")
    public String manageCategory(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model) {

        // Lọc và tìm kiếm
        Page<Category> categories = categoryService.findCategories(search, page, size);

        model.addAttribute("categories", categories.getContent());
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        return "category-management";
    }

    // Hiển thị form thêm/sửa category
    @GetMapping("/category-form")
    public String showCategoryForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        Category category = id != null ? categoryService.findById(id) : new Category();
        List<String> typeList = FoodType.AllValue();
        model.addAttribute("category", category);
        model.addAttribute("typeList", typeList);
        return "category-form";
    }

    @GetMapping("/category-create")
    public String showCategoryCreate(Model model) {
        List<String> typeList = FoodType.AllValue();
        model.addAttribute("typeList", typeList);
        return "category-create";
    }

    // Lưu category
    private void updateNullFields(Object target, Object source) {
        if (target == null || source == null) return;

        Class<?> clazz = target.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Đảm bảo có thể truy cập vào cả các field private
            try {
                Object targetValue = field.get(target);
                if (targetValue == null) {
                    Object sourceValue = field.get(source);
                    field.set(target, sourceValue);
                }
            } catch (IllegalAccessException e) {
                // Xử lý lỗi nếu xảy ra
                e.printStackTrace();
            }
        }
    }
    @PostMapping("/save-category")
    public String saveCategory(@ModelAttribute("category") Category category) {
        Category isExisted = categoryService.findById(category.getId());
        if(isExisted!=null){
            updateNullFields(category, isExisted);
        }
        categoryService.save(category);
        return "redirect:/category-management";
    }

    @PostMapping("/init-category")
    public String saveCategory(@ModelAttribute("category") Category category, RedirectAttributes redirectAttributes) {
        categoryService.save(category);

        // Redirect with the ID of the newly saved category
        return "redirect:/category-management";
    }


    // Xóa category
    @PostMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") Long id) {
        categoryService.deleteById(id);
        return "redirect:/category-management";
    }

}
