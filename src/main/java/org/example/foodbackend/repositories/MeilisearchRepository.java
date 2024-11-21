package org.example.foodbackend.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import com.meilisearch.sdk.SearchRequest;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import org.example.foodbackend.entities.Ingredient;
import org.example.foodbackend.entities.dto.MeilisearchIngredient;
import org.example.foodbackend.entities.enums.SearchStatus;
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
    private final IngredientRepository ingredientRepository;

    public MeilisearchRepository(IngredientRepository ingredientRepository) throws IOException {
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
        this.ingredientRepository = ingredientRepository;
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
                configureIndexSettings(client.index(INDEX_NAME));
                System.out.println("Index already exists: " + INDEX_NAME);
                return client.index(INDEX_NAME);
            }
        } catch (MeilisearchException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void configureIndexSettings(Index index) throws MeilisearchException {

        index.updateSortableAttributesSettings(new String[]{"id", "name", "type", "calo", "protein", "fat", "satFat", "fiber", "carb"});
        System.out.println("Index settings updated for filtering attributes.");
    }

    public void clearAllSearchedIngredient(){
        try{
            ingredientRepository.changeAllVerified(SearchStatus.PENDING);
            index.deleteAllDocuments();
        }
        catch(Exception e){
            e.printStackTrace();
        }
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

    public List<String> suggestIngredients(String query) {
        try {
            SearchRequest searchRequest = SearchRequest.builder()
                    .q(query)
                    .limit(10)
                    .build();

            ArrayList<HashMap<String, Object>> hits = index.search(searchRequest).getHits();

            List<String> names = new ArrayList<>();
            for (Map<String, Object> hit : hits) {
                String name = objectMapper.convertValue(hit, MeilisearchIngredient.class).getName();
                names.add(name);
            }

            System.out.println("Found " + names.size() + " names for query: " + query);
            return names;

        } catch (MeilisearchException e) {
            e.printStackTrace();
            return new ArrayList<>(); // Return an empty list in case of an error
        }
    }

    public List<Long> searchIngredients(String query, int page, int size, String sort, String order) {
        try {
            int offset = (page - 1) * size;
            SearchRequest searchRequest = SearchRequest.builder()
                    .q(query)
                    .offset(offset)
                    .limit(size)
                    .sort(new String[]{sort + ":" + order}).build();


            // Thực hiện tìm kiếm
            ArrayList<HashMap<String, Object>> hits = index.search(searchRequest).getHits();

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
    public Integer getCountSearchedIngredient() {
        try {
            // Perform a search with an empty query to get the document count
            SearchRequest searchRequest = SearchRequest.builder()
                    .q("") // Empty query
                    .limit(0) // No need to retrieve actual documents
                    .build();

            return index.search(searchRequest).getHits().size(); // Get the total number of hits
        } catch (MeilisearchException e) {
            e.printStackTrace();
            return 0; // Return 0 in case of an error
        }
    }
}
