package pro.sky.recipeapp.services;

import pro.sky.recipeapp.model.Recipe;

public interface RecipeService {

    void addRecipe(Recipe recipe);

    Recipe getRecipe(int id);
}
