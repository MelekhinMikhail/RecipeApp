package pro.sky.recipeapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.TreeMap;

@Data
@Schema(description = "Сущность рецепта")
@NoArgsConstructor
public class Recipe {
    @Schema(description = "Имя")
    private String name;
    @Schema(description = "Время готовки")
    private int cookingTime;
    @Schema(description = "Ингредиенты")
    private LinkedList<Ingredient> ingredients;
    @Schema(description = "Шаги приготовления")
    private TreeMap<Integer, String> steps;

    @Override
    public String toString() {
        return "Рецепт: "+name+",   Время готовки: "+cookingTime+" минут"+((ingredients.size()==0) ? "." : "Ингредиентов: "+ingredients.size())+
                ((steps.size()==0) ? "" : "Шагов: "+steps.size());
    }
}
