package pro.sky.recipeapp.services;

import java.io.File;

public interface FilesService {
    boolean saveToRecipesFile(String json);

    boolean saveToIngredientsFile(String json);

    String readFromRecipesFile();

    String readFromIngredientsFile();

    boolean cleanRecipesFile();

    boolean cleanIngredientsFile();

    File getRecipesFile();

    File getIngredientsFile();
}
