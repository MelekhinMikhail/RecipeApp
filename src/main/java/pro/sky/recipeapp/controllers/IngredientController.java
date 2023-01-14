package pro.sky.recipeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.services.IngredientService;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/add")
    public String add(@RequestParam String name, @RequestParam int count, @RequestParam String measureUnit) {
        ingredientService.addIngredient(new Ingredient(name, count, measureUnit));
        return "Success!";
    }

    @GetMapping("/get")
    public String get(@RequestParam int id) {
        if (ingredientService.getIngredient(id) == null) return "Такого ингредиента нет.";
        else return ingredientService.getIngredient(id).toString();
    }
}
