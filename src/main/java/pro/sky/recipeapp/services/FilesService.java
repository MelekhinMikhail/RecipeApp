package pro.sky.recipeapp.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    boolean saveToRecipesFile(String json);

    boolean saveToIngredientsFile(String json);

    String readFromRecipesFile();

    String readFromIngredientsFile();

    boolean cleanRecipesFile();

    boolean cleanIngredientsFile();

    File getRecipesFile();

    File getIngredientsFile();

    Path createTempFile(String suffix);
}
