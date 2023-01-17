package pro.sky.recipeapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.services.IngredientService;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long count = 0;

    public HashMap<Long, Ingredient> storage = new HashMap<>();

    @Override
    public void addIngredient(Ingredient ingredient) {
        if (!storage.containsValue(ingredient)) {
            storage.put(count++, ingredient);
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
            return true;
        }
        return false;
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return new LinkedList<>(storage.values());
    }
}
