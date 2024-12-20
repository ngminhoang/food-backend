package org.example.foodbackend.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import com.meilisearch.sdk.model.SearchResult;
import com.meilisearch.sdk.model.Settings;
import org.example.foodbackend.entities.dto.MeilisearchIngredient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class MeilisearchRepository {

    private final Client client;
    private final Index index;
    private static final String INDEX_NAME = "food_db"; // Specify your index name
    private final ObjectMapper objectMapper;

    public MeilisearchRepository() throws IOException {
        // Load Meilisearch configuration from properties file
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        }

        String host = properties.getProperty("meili.host");
        String masterKey = properties.getProperty("meili.masterKey");

        Config config = new Config(host, masterKey);
        this.client = new Client(config);
        this.objectMapper = new ObjectMapper();

        // Initialize or create the index
        this.index = initializeIndex();
    }

    private Index initializeIndex() {
        try {
            // Check if index exists; if not, create it
            if (client.getIndex(INDEX_NAME) == null) {
                client.createIndex(INDEX_NAME);
                Index createdIndex = client.getIndex(INDEX_NAME);
                System.out.println("Index created: " + INDEX_NAME);

                // Configure index with attributes
                configureIndexSettings(createdIndex);
                return createdIndex;
            } else {
                System.out.println("Index already exists: " + INDEX_NAME);
                return client.index(INDEX_NAME);
            }
        } catch (MeilisearchException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void configureIndexSettings(Index index) throws MeilisearchException {
        // Define the attributes structure of the index
        Settings settings = new Settings();
        settings.setFilterableAttributes(new String[]{"id", "name", "type", "calo", "protein", "fat", "satFat", "fiber", "carb"});
        index.updateSettings(settings);
        System.out.println("Index settings updated for filtering attributes.");
    }

    // Method to insert a MeilisearchIngredient into the index
    public void insertIngredient(MeilisearchIngredient ingredient) {
        try {
            String ingredientJson = objectMapper.writeValueAsString(ingredient);
            index.addDocuments(ingredientJson);
            System.out.println("Ingredient added to Meilisearch index: " + ingredient);
        } catch (MeilisearchException | IOException e) {
            e.printStackTrace();
        }
    }

    public List<Long> searchIngredients(String query, int page, int size) {
        try {
            // Tính toán offset dựa trên page và size
            int offset = (page - 1) * size;

            // Tạo SearchRequest với query, offset và limit
            SearchRequest searchRequest = new SearchRequest(query)
                    .setOffset(offset)
                    .setLimit(size);

            // Thực hiện tìm kiếm
            SearchResult searchResult = (SearchResult) index.search(searchRequest);

            // Kiểm tra dữ liệu hits trong SearchResult
            ArrayList<HashMap<String, Object>> hits = searchResult.getHits();

            // Chuyển đổi danh sách các Map thành danh sách MeilisearchIngredient
            List<Long> ingredients = new ArrayList<>();
            for (Map<String, Object> hit : hits) {
                Long ingredient = objectMapper.convertValue(hit, MeilisearchIngredient.class).getId();
                ingredients.add(ingredient);
            }

            System.out.println("Found " + ingredients.size() + " ingredients for query: " + query);
            return ingredients;

        } catch (MeilisearchException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    // Additional methods for working with the index can go here
}
