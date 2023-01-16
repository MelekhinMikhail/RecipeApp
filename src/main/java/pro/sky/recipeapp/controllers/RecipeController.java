package pro.sky.recipeapp.controllers;

import org.springframework.web.bind.annotation.*;
import pro.sky.recipeapp.model.Recipe;
import pro.sky.recipeapp.services.RecipeService;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/add")
    public String add(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return "Success!";
    }

    @GetMapping("/get")
    public Recipe get(@RequestParam int id) {
        if (recipeService.getRecipe(id) == null) {
            throw new RuntimeException();
        } else {
            return recipeService.getRecipe(id);
        }
    }
}
