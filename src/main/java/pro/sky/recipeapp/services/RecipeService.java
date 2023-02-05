package pro.sky.recipeapp.services;

import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.model.Recipe;

import java.nio.file.Path;
import java.util.List;

public interface RecipeService {

    void addRecipe(Recipe recipe);

    Recipe getRecipe(long id);

    boolean deleteRecipeById(long id);

    boolean editRecipe(long id, Recipe recipe);

    void deleteAllRecipes();

    List<Recipe> getAllRecipes();

    List<Recipe> getRecipesByIngredient(Ingredient ingredient);

    Recipe getRecipeByIngredients(List<Ingredient> list);

    Path createAllRecipesFile();
}
