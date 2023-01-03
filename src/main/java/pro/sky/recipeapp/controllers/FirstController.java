package pro.sky.recipeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {

    @GetMapping("/")
    public String page() {
        return "Приложение запущено.";
    }

    @GetMapping("/info")
    public String info() {
        return """
                Имя ученика: Михаил
                Название проекта: RecipeApp
                Дата создания проекта: 03.01.2023
                Описание проекта: Приложение для сайта рецептов.""";
    }
}
