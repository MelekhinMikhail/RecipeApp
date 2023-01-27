package pro.sky.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.services.FilesService;
import pro.sky.recipeapp.services.IngredientService;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long count = 0;
    private static TreeMap<Long, Ingredient> storage = new TreeMap<>();
    final private FilesService filesService;

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromIngredientsFile();
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (!storage.containsValue(ingredient)) {
            storage.put(count++, ingredient);
            saveToIngredientsFile();
        }
    }

    @Override
    public Ingredient getIngredient(long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public boolean editIngredient(long id, Ingredient ingredient) {
        if (storage.size() == 0) {
            return false;
        }
        if (storage.containsKey(id)) {
            storage.put(id, ingredient);
            saveToIngredientsFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteIngredientById(long id) {
        if (storage.size() == 0) {
            return false;
        }
        if (storage.containsKey(id)) {
            storage.remove(id);
            saveToIngredientsFile();
            return true;
        }
        return false;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return new LinkedList<>(storage.values());
    }

    private void saveToIngredientsFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(storage);
            filesService.saveToIngredientsFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromIngredientsFile() {
        try {
            String json = filesService.readFromIngredientsFile();
            storage = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, Ingredient>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static TreeMap<Long, Ingredient> getStorage() {
        return storage;
    }

    public static long getCount() {
        return count;
    }

    public static void setCount(long id) {
        count = id;
    }
}
