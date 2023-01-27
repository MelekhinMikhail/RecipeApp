package pro.sky.recipeapp.services;

public interface FilesService {
    boolean saveToRecipesFile(String json);

    boolean saveToIngredientsFile(String json);

    String readFromRecipesFile();

    String readFromIngredientsFile();

    boolean cleanRecipesFile();

    boolean cleanIngredientsFile();
}
