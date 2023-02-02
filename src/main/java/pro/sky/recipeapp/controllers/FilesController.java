package pro.sky.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.recipeapp.services.FilesService;

import java.io.*;

@RestController
@RequestMapping("/files")
@Tag(name = "Файлы", description = "Операции для работы с файлами.")
public class FilesController {

    private final FilesService filesService;

    public FilesController(FilesService filesService) {
        this.filesService = filesService;
    }

    @Operation(
            summary = "Скачать файл с рецептами.",
            description = "Эта операция позволяет скачать JSON-файл со всеми рецептами."
    )
    @GetMapping("/export-recipes")
    public ResponseEntity<InputStreamResource> downloadRecipesFile() throws FileNotFoundException {
        File file = filesService.getRecipesFile();
        if (file.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(
            summary = "Отправить и заменить файл с рецептами.",
            description = "Эта операция позволяет отправить файл на сервер и заменить старый файл с рецептами."
    )
    @PostMapping(value = "/import-recipes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipesFile(@RequestParam MultipartFile file) {
        filesService.cleanRecipesFile();
        File recipesFile = filesService.getRecipesFile();

        try (FileOutputStream fos = new FileOutputStream(recipesFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(
            summary = "Отправить и заменить файл с ингредиентами.",
            description = "Эта операция позволяет отправить файл на сервер и заменить старый файл с ингредиентами."
    )
    @PostMapping(value = "/import-ingredients", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngredientsFile(@RequestParam MultipartFile file) {
        filesService.cleanIngredientsFile();
        File ingredientsFile = filesService.getIngredientsFile();

        try (FileOutputStream fos = new FileOutputStream(ingredientsFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
