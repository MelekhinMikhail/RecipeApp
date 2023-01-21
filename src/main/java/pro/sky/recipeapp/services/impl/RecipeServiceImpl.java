package pro.sky.recipeapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.model.Recipe;
import pro.sky.recipeapp.services.RecipeService;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long count = 0;

    private static Map<Long, Recipe> storage = new TreeMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        if (!storage.containsValue(recipe)) {
            storage.put(count++, recipe);
        }
    }

    @Override
    public Recipe getRecipe(long id) {
        return storage.get(id);
    }

    @Override
    public boolean deleteRecipeById(long id) {
        var removed = storage.remove(id);
        return removed != null;
    }

    @Override
    public boolean editRecipe(long id, Recipe recipe) {
        if (storage.containsKey(id)) {
            storage.put(id, recipe);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteAllRecipes() {
        storage.clear();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        if (storage.size() == 0) {
            return Collections.emptyList();
        }
        return new LinkedList<>(storage.values());
    }

    @Override
    public List<Recipe> getRecipesByIngredient(Ingredient ingredient) {
        List<Recipe> list = new LinkedList<>();
        for (Recipe recipe : storage.values()) {
            if (recipe.getIngredients().contains(ingredient)) {
                list.add(recipe);
            }
        }
        return list;
    }

    @Override
    public Recipe getRecipeByIngredients(List<Ingredient> list) {
        if (list.size() == 0) {
            return null;
        }
        for (Recipe recipe : storage.values()) {
            if (recipe.getIngredients().containsAll(list)) {
                return recipe;
            }
        }
        return null;
    }
}
