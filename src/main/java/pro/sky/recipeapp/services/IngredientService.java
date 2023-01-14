package pro.sky.recipeapp.services;

import pro.sky.recipeapp.model.Ingredient;

public interface IngredientService {

    void addIngredient(Ingredient ingredient);

    Ingredient getIngredient(int id);
}