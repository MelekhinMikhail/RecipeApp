package pro.sky.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.model.Recipe;
import pro.sky.recipeapp.services.RecipeService;

import java.util.List;

@RestController
@RequestMapping("/recipe")
@Tag(name = "Рецепты", description = "CRUD-операции для работы с рецептами.")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @Operation(
            summary = "Добавить рецепт.",
            description = "Эта операция позволяет добавить созданный рецепт."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно добавлен"
            )
    })
    @PostMapping("/")
    public ResponseEntity<Recipe> add(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);
        return ResponseEntity.ok(recipe);
    }

    @Operation(
            summary = "Получить рецепт по id.",
            description = "Эта операция позволяет получить рецепт по id."
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> get(@PathVariable @Parameter(description = "Идентификатор рецепта") long id) {
        if (recipeService.getRecipe(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(recipeService.getRecipe(id));
        }
    }

    @Operation(
            summary = "Отредактировать рецепт по id.",
            description = "Эта операция позволяет заменить рецепт на новый по указанному id."
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно заменен"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> edit(@PathVariable @Parameter(description = "Идентификатор рецепта") long id, @RequestBody Recipe recipe) {
        if (recipeService.editRecipe(id, recipe)) {
            return ResponseEntity.ok(recipe);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Удалить рецепт по id.",
            description = "Эта операция позволяет удалить рецепт по id."
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт успешно удален"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteById(@PathVariable @Parameter(description = "Идентификатор рецепта") long id) {
        if (recipeService.deleteRecipeById(id)) {
            return ResponseEntity.ok(id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Удалить все рецепты.",
            description = "Эта операция позволяет удалить все рецепты."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Все рецепты успешно добавлены"
            )
    })
    @DeleteMapping("/")
    public ResponseEntity<Void> deleteAll() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Получить все рецепты.",
            description = "Эта операция позволяет получить все рецепты."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты найдены"
            )
    })
    @GetMapping("/")
    public ResponseEntity<List<Recipe>> getAll() {
        List<Recipe> list = recipeService.getAllRecipes();
        if (list.size() == 0) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(
            summary = "Получить рецепты по ингредиенту.",
            description = "Эта операция позволяет получить все рецепты с указанным ингредиентом."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепты найдены"
            )
    })
    @PostMapping("/ingredient")
    public ResponseEntity<List<Recipe>> getRecipesByIngredient(@RequestBody Ingredient ingredient) {
        List<Recipe> list = recipeService.getRecipesByIngredient(ingredient);
        if (list.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(list);
        }
    }

    @Operation(
            summary = "Получить рецепт по ингредиентам.",
            description = "Эта операция позволяет получить рецепт с указанными ингредиентами."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Рецепт найден"
            )
    })
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
