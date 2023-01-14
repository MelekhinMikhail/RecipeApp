package pro.sky.recipeapp.services;

import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Ingredient;

import java.util.HashMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static int count = 0;

    public HashMap<Integer, Ingredient> storage = new HashMap<>();

    @Override
    public void addIngredient(Ingredient ingredient) {
        storage.put(count++, ingredient);
    }

    @Override
    public Ingredient getIngredient(int id) {
        return storage.getOrDefault(id, null);
    }
}
