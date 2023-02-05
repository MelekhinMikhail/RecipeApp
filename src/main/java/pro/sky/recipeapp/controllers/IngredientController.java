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
import pro.sky.recipeapp.services.IngredientService;

import java.util.List;

@RestController
@RequestMapping("/ingredient")
@Tag(name = "Ингредиенты", description = "CRUD-операции для работы с ингредиентами.")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @Operation(
            summary = "Добавить ингредиент.",
            description = "Эта операция позволяет добавить созданный ингредиент."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно добавлен"
            )
    })
    @PostMapping("/")
    public ResponseEntity<Ingredient> add(@RequestBody Ingredient ingredient) {
        ingredientService.addIngredient(ingredient);
        return ResponseEntity.ok(ingredient);
    }

    @Operation(
            summary = "Получить ингредиент по id.",
            description = "Эта операция позволяет получить ингредиент по id."
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент найден"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> get(@PathVariable long id) {
        if (ingredientService.getIngredient(id) == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(ingredientService.getIngredient(id));
        }
    }

    @Operation(
            summary = "Отредактировать ингредиент по id.",
            description = "Эта операция позволяет заменить ингредиент на новый по указанному id."
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно заменен"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> edit(@PathVariable long id, @RequestBody Ingredient ingredient) {
        if (ingredientService.editIngredient(id, ingredient)) {
            return ResponseEntity.ok(ingredient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Удалить ингредиент по id.",
            description = "Эта операция позволяет удалить ингредиент по id."
    )
    @Parameters(value = {
            @Parameter(name = "id", example = "0")
    })
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиент успешно удален"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        if (ingredientService.deleteIngredientById(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Получить все ингредиенты.",
            description = "Эта операция позволяет получить все ингредиенты."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Ингредиенты найдены"
            )
    })
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
