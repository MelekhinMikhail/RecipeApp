package pro.sky.recipeapp.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.sky.recipeapp.services.FilesService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.file}")
    private String dataFilePath;

    @Value("${name.of.recipes.data.file}")
    private String recipesFileName;

    @Value("${name.of.ingredients.data.file}")
    private String ingredientsFileName;

    @Override
    public boolean saveToRecipesFile(String json) {
        try {
            cleanRecipesFile();
            Files.writeString(Path.of(dataFilePath, recipesFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean saveToIngredientsFile(String json) {
        try {
            cleanIngredientsFile();
            Files.writeString(Path.of(dataFilePath, ingredientsFileName), json);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public String readFromRecipesFile() {
        try {
            return Files.readString(Path.of(dataFilePath, recipesFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String readFromIngredientsFile() {
        try {
            return Files.readString(Path.of(dataFilePath, ingredientsFileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanRecipesFile() {
        try {
            Path path = Path.of(dataFilePath, recipesFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean cleanIngredientsFile() {
        try {
            Path path = Path.of(dataFilePath, ingredientsFileName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getRecipesFile() {
        return new File(dataFilePath+"/"+recipesFileName);
    }

    @Override
    public File getIngredientsFile() {
        return new File(dataFilePath+"/"+ingredientsFileName);
    }

    @Override
    public Path createTempFile(String suffix) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", suffix);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
