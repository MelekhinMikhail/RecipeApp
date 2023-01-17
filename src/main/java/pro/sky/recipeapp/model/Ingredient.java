package pro.sky.recipeapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ingredient {
    private String name;
    private int count;
    private String measureUnit;
    @Override
    public String toString() {
        return name+", кол-во: "+count+", ед. изм.: "+measureUnit;
    }
}
