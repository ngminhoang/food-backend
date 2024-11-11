package org.example.foodbackend.other_services;

public final class APIConstants {

    public static final String API_URL_ROOT = "http://localhost:5000/api";
    public static final String CALCULATE_API = "/data?calo=%f&protein=%f&fat=%f&sat_fat=%f&fiber=%f&carb=%f";
    public static final String CALCULATE_BY_BODY_API = "/data?weight_kg=%f&height_cm=%f&age=%f&gender=%f&activity_level=%f&moderately_active=%f";

    private APIConstants() {
        // Prevent instantiation
    }
}
