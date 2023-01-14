package pro.sky.recipeapp.model;

import lombok.Data;

import java.util.LinkedList;
import java.util.TreeMap;

@Data
public class Recipe {
    private String name;
    private int cookingTime;
    private LinkedList<Ingredient> ingredients;
    private TreeMap<Integer, String> steps;

    public Recipe(String name, int cookingTime) {
        if (name == null || name.isEmpty() || name.isBlank() || cookingTime <= 0) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.cookingTime = cookingTime;
        ingredients = new LinkedList<>();
        steps = new TreeMap<>();
    }

    @Override
    public String toString() {
        return "Рецепт: "+name+",   Время готовки: "+cookingTime+" минут"+((ingredients.size()==0) ? "." : "Ингредиентов: "+ingredients.size())+
                ((steps.size()==0) ? "" : "Шагов: "+steps.size());
    }
}
