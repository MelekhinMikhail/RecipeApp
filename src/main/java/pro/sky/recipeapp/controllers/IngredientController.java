package pro.sky.recipeapp.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.services.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping("/")
    public ResponseEntity<Ingredient> add(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> get(@PathVariable long id) {
        if (ingredientService.getIngredient(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ingredientService.getIngredient(id));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> edit(@PathVariable long id, @RequestBody Ingredient ingredient) {
        if (ingredientService.editIngredient(id, ingredient)) {
            return ResponseEntity.ok(ingredient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        if (ingredientService.deleteIngredientById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<Ingredient>> getAll() {
        List<Ingredient> list = ingredientService.getAllIngredients();
        if (list.size() != 0) {
            return ResponseEntity.ok(list);
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
