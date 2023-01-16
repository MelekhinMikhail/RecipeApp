package pro.sky.recipeapp.controllers;

import org.springframework.web.bind.annotation.*;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.services.IngredientService;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/add")
    public String add(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return "Success!";
    }

    @GetMapping("/get")
    public Ingredient get(@RequestParam int id) {
        if (ingredientService.getIngredient(id) == null) {
            throw new RuntimeException();
        } else {
            return ingredientService.getIngredient(id);
        }
    }
}
