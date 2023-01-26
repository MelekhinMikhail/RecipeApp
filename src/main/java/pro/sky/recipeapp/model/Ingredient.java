package pro.sky.recipeapp.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Сущность ингредиента")
public class Ingredient {
    @Schema(description = "Имя")
    private String name;
    @Schema(description = "Кол-во")
    private int count;
    @Schema(description = "Единица измерения")
    private String measureUnit;
    @Override
    public String toString() {
        return name+", кол-во: "+count+", ед. изм.: "+measureUnit;
    }
}
