package org.example.foodbackend;

import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

@PlanningEntity
public class Food {

    private String name;
    private double nutrition;
    private double price;
    private Integer quantity; // Chuyển từ int sang Integer

    public Food() {
        // Mặc định cho OptaPlanner sử dụng
    }

    public Food(String name, double nutrition, double price) {
        this.name = name;
        this.nutrition = nutrition;
        this.price = price;
        this.quantity = 0; // Khởi tạo số lượng ban đầu
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getNutrition() {
        return nutrition;
    }

    public void setNutrition(double nutrition) {
        this.nutrition = nutrition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @PlanningVariable(valueRangeProviderRefs = "foodRange")
    public Integer getQuantity() { // Đảm bảo trả về Integer
        return quantity;
    }

    public void setQuantity(Integer quantity) { // Thay đổi kiểu tham số từ int sang Integer
        this.quantity = quantity;
    }
}