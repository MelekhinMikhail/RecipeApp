package pro.sky.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pro.sky.recipeapp.model.Ingredient;
import pro.sky.recipeapp.model.Recipe;
import pro.sky.recipeapp.services.FilesService;
import pro.sky.recipeapp.services.RecipeService;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Service
public class RecipeServiceImpl implements RecipeService {
    private static long count = 0;
    final private FilesService filesService;
    private static TreeMap<Long, Recipe> recipes = new TreeMap<>();
    private Map<Long, TreeMap<Long, Recipe>> fileStorage = new TreeMap<>();

    public RecipeServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromRecipesFile();
    }

    @Override
    public void addRecipe(Recipe recipe) {
        if (!recipes.containsValue(recipe)) {
            recipes.put(count++, recipe);
            saveToRecipesFile();
            if (recipe.getIngredients().size() != 0) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (!IngredientServiceImpl.getStorage().containsValue(ingredient)) {
                        IngredientServiceImpl.getStorage().put(IngredientServiceImpl.getCount(), ingredient);
                        IngredientServiceImpl.setCount(IngredientServiceImpl.getCount() + 1);
                    }
                }
                saveToIngredientsFile();
            }
        }
    }

    @Override
    public Recipe getRecipe(long id) {
        return recipes.get(id);
    }

    @Override
    public boolean deleteRecipeById(long id) {
        var removed = recipes.remove(id);
        saveToRecipesFile();
        return removed != null;
    }

    @Override
    public boolean editRecipe(long id, Recipe recipe) {
        if (recipes.containsKey(id)) {
            recipes.put(id, recipe);
            saveToRecipesFile();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void deleteAllRecipes() {
        recipes.clear();
        saveToRecipesFile();
    }

    @Override
    public List<Recipe> getAllRecipes() {
        if (recipes.size() == 0) {
            return Collections.emptyList();
        }
        return new LinkedList<>(recipes.values());
    }

    @Override
    public List<Recipe> getRecipesByIngredient(Ingredient ingredient) {
        List<Recipe> list = new LinkedList<>();
        for (Recipe recipe : recipes.values()) {
            if (recipe.getIngredients().contains(ingredient)) {
                list.add(recipe);
            }
        }
        return list;
    }

    @Override
    public Recipe getRecipeByIngredients(List<Ingredient> list) {
        if (list.size() == 0) {
            return null;
        }
        for (Recipe recipe : recipes.values()) {
            if (recipe.getIngredients().containsAll(list)) {
                return recipe;
            }
        }
        return null;
    }
    private void saveToRecipesFile() {
        try {
            fileStorage.clear();
            fileStorage.put(count, recipes);
            String json = new ObjectMapper().writeValueAsString(fileStorage);
            filesService.saveToRecipesFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveToIngredientsFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(IngredientServiceImpl.getStorage());
            filesService.saveToIngredientsFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void readFromRecipesFile() {
        try {
            String json = filesService.readFromRecipesFile();
            fileStorage = new ObjectMapper().readValue(json, new TypeReference<TreeMap<Long, TreeMap<Long, Recipe>>>() {
            });
            for (Map.Entry<Long, TreeMap<Long, Recipe>> entry : fileStorage.entrySet()) {
                count = entry.getKey();
                recipes = entry.getValue();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Path createAllRecipesFile() {
        Path path = filesService.createTempFile("allRecipes");
        for (Recipe recipe : recipes.values()) {
            try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
                writer.append(recipe.getName()+"\n");
                writer.append("Время приготовления: " + recipe.getCookingTime() + " минут.\n");
                writer.append("Ингредиенты:\n");
                for (Ingredient ingredient : recipe.getIngredients()) {
                    writer.append(" - " + ingredient.getName() + " ~ " + (ingredient.getCount() == 0 ? "" : ingredient.getCount()+" ") + ingredient.getMeasureUnit() + "\n");
                }
                writer.append("Инструкция приготовления:\n");
                for (Map.Entry<Integer, String> entry : recipe.getSteps().entrySet()) {
                    writer.append(entry.getKey() + ". " + entry.getValue() + "\n");
                }
                writer.append("\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return path;
    }
}
