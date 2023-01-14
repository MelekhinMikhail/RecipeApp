package pro.sky.recipeapp.services;

import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Recipe;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static int count = 0;

    public static Map<Integer, Recipe> storage = new HashMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        storage.put(count++, recipe);
    }

    @Override
    public Recipe getRecipe(int id) {
        return storage.getOrDefault(id, null);
    }


}
