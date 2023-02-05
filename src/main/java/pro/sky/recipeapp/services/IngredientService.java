package pro.sky.recipeapp.services;

import pro.sky.recipeapp.model.Ingredient;

import java.util.List;

public interface IngredientService {

    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(long id);

    boolean editIngredient(long id, Ingredient ingredient);

    boolean deleteIngredientById(long id);

    List<Ingredient> getAllIngredients();
}