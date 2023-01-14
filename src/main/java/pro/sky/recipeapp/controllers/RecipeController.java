package pro.sky.recipeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recipeapp.model.Recipe;
import pro.sky.recipeapp.services.RecipeService;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/add")
    public String add(@RequestParam String name, @RequestParam int time) {
        recipeService.addRecipe(new Recipe(name, time));
        return "Success!";
    }

    @GetMapping("/get")
    public String get(@RequestParam int id) {
        if (recipeService.getRecipe(id) == null) return "Такого рецепта нет.";
        else return recipeService.getRecipe(id).toString();
    }
}
