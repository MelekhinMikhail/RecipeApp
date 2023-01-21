package pro.sky.recipeapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.model.Recipe;
import pro.sky.recipeapp.services.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/")
    public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> get(@PathVariable long id) {
        if (recipeService.getRecipe(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipeService.getRecipe(id));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> edit(@PathVariable long id, @RequestBody Recipe recipe) {
        if (recipeService.editRecipe(id, recipe)) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable long id) {
        if (recipeService.deleteRecipeById(id)) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<Recipe>> getAll() {
        List<Recipe> list = recipeService.getAllRecipes();
        if (list.size() == 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @PostMapping("/ingredient")
    public ResponseEntity<List<Recipe>> getRecipesByIngredient(@RequestBody Ingredient ingredient) {
        List<Recipe> list = recipeService.getRecipesByIngredient(ingredient);
        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Recipe> getRecipeByIngredients(@RequestBody List<Ingredient> list) {
        Recipe recipe = recipeService.getRecipeByIngredients(list);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipe);
        }
    }
}
