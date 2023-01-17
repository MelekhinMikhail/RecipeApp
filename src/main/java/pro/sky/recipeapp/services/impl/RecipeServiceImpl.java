package pro.sky.recipeapp.services.impl;

import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.model.Recipe;
import pro.sky.recipeapp.services.RecipeService;

import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long count = 0;

    public static Map<Long, Recipe> storage = new TreeMap<>();

    @Override
    public void addRecipe(Recipe recipe) {
        if (!storage.containsValue(recipe)) {
            storage.put(count++, recipe);
        }
    }

    @Override
    public Recipe getRecipe(long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public boolean deleteRecipeById(long id) {
        if (storage.containsKey(id)) {
            storage.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean editRecipe(long id, Recipe recipe) {
        if (storage.containsKey(id)) {
            storage.remove(id);
            storage.put(id, recipe);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteAllRecipes() {
        storage = new TreeMap<>();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        if (storage.size() == 0) {
            return null;
        }
        List<Recipe> list = new LinkedList<>();
        for (Map.Entry<Long, Recipe> entry : storage.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

    @Override
    public List<Recipe> getRecipesByIngredient(Ingredient ingredient) {
        if (storage.size() == 0) {
            return null;
        }
        List<Recipe> list = new LinkedList<>();
        for (Recipe recipe : storage.values()) {
            if (recipe.getIngredients().contains(ingredient)) {
                list.add(recipe);
            }
        }
        if (list.size() != 0) {
            return list;
        } else {
            return null;
        }
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
